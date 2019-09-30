<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<title>海文在线短信平台</title>
<link type="text/css" rel="stylesheet" href="css/sms.css" />
	<script type="text/javascript" src="scripts/jquery.js"></script>
	<script>
		$(function () {
		    showMsg(1,3);
        });

        //查询当前短消息
        function showMsgById(id) {
            window.location = "${pageContext.request.contextPath}/mesS/lookMsg?id=" + id;
        }

       	//用ajax显示分页信息
		function showMsg(pageNo,pageSize) {
            $.post("${pageContext.request.contextPath}/mesS/show",{"pageNo":pageNo,"pageSize":pageSize},function (data) {
                //alert(JSON.stringify(data));//把json对象转换成字符串
                //局部刷新,进行页面的拼接
                var $ul = $(".messageList>ul");
                $ul.empty();//清空其子元素
                for (var i = 0; i < data.list.length; i++) {
                    var msg = data.list[i];
                    var $li = "";
                    if (msg.state == 1) {
                        $li = $("<li class='unReaded'></li>");//创建li元素
                    } else {
                        $li = $("<li></li>");
                    }
                    var $em1 = $("<em>" + msg.msgCreateDate + "</em>");
                    var $em2 = $("<em><a href='${pageContext.request.contextPath}/userS/queryUserById/"+msg.sendId+"'>回信</a></em>");
                    var $em3 = $("<em><a href='${pageContext.request.contextPath}/mesS/delMsg/"+msg.id+"' class='del'>删除</a></em>");
                    var $p = $("<p></p>");//创建一个p标签
                    var $strong = $("<strong><a href="+"javascript:showMsgById('"+msg.id+"')>" + msg.title + "</a></strong>");
                    var $a = "";
                    if (msg.msgContent.length > 8) {
                        $a = $("<p>" + msg.msgContent.substring(0, 8) + "......</p>");
                    } else {
                        $a = $("<p>" + msg.msgContent + "</p>");
                    }
                    $p.append($strong);
                    $p.append($a);
                    $li.append($em1);
                    $li.append($em2);
                    $li.append($em3);
                    $li.append($p);
                    $ul.append($li);
                    /*---------------------------------------------------------------------*/


                }

                $a = $("#btn>a");
                $a.eq(0).attr("href", "javascript:showMsg('" + 1 + "','" + pageSize + "')");// showMsgs('1','5')
                $a.eq(1).attr("href", "javascript:showMsg('" + data.prePage + "','" + pageSize + "')");
                $a.eq(2).attr("href", "javascript:showMsg('" + data.nextPage + "','" + pageSize + "')");
                $a.eq(3).attr("href", "javascript:showMsg('" + data.pages + "','" + pageSize + "')");
                $span = $("#btn>span");
                $span.eq(0).html(data.pageNum);
                $span.eq(1).html(data.pages);


            },"json")
        }

	</script>
</head>
<body>
	<div id="main">
		<div class="mainbox">
			<div class="title myMessage png"></div>
			<%--导入开头--%>
			<%@ include file="title.jsp"%>
			<!--错误信息  -->
			<div id="error"></div>
			<!--短消息列表  -->
			<%--<c:forEach items="${data.list}" var="list">--%>
			<div class="content messageList">
			  <ul>
	<%--			  &lt;%&ndash;判断短信是否已读&ndash;%&gt;
			    <li class="${list.state==1?'unReaded':''}">
					&lt;%&ndash;发送时间&ndash;%&gt;
			       <em>${list.msgCreateDate}</em>
						&lt;%&ndash;回信先根据ID查询到user，再对指定人进行回信&ndash;%&gt;
			       <em><a href="${pageContext.request.contextPath}/userS?param=queryUserById&sendId=${list.sendId}">回信</a></em>
						&lt;%&ndash;跳转到删除操作，并把当前id传过去&ndash;%&gt;
			       <em><a href="#" class="delMsg">删除</a></em>
						&lt;%&ndash;用于获取当前短信的id&ndash;%&gt;
						<input type="hidden" class="MsgId" value="${list.id}">
			       <p>
					   &lt;%&ndash;短信标题&ndash;%&gt;
			          	<strong><a href="${pageContext.request.contextPath}/mesS?param=lookMes&id=${list.id}">${list.title}</a></strong>
					   
						<c:choose>
							&lt;%&ndash;判断内容，当短信内容大于8的时候&ndash;%&gt;
					   		<c:when test="${fn:length(list.msgContent)>8}">
						   &lt;%&ndash;进行字符串切割&ndash;%&gt;
							${fn:substring(list.msgContent, 0, 8)}...
					   		</c:when>
							&lt;%&ndash;当小于8的时候直接打印&ndash;%&gt;
							<c:when test="${fn:length(list.msgContent)<8}">
								${list.msgContent}
							</c:when>
						</c:choose>

			       </p>

			       
			    </li>--%>
			  </ul>
			</div>
			<%--</c:forEach>--%>
			<!--分页栏 -->
			<div align="center" style="margin-top:10px" id="btn">
				<%-- <a href="javascript:go('1','${data.pageSize}')">首页</a>&nbsp;&nbsp;&nbsp;
                 <a href="javascript:go('${data.prePage}','${data.pageSize}')">上一页</a>&nbsp;&nbsp;
                 <a href="javascript:go('${data.nextPage}','${data.pageSize}')">下一页</a>&nbsp;&nbsp;
                 ${data.pageNo}/${data.totalPages}&nbsp;&nbsp;
                 <a href="javascript:go('${data.totalPages}','${data.pageSize}')">最后一页</a>--%>
				<a href="#">首页</a>&nbsp;&nbsp;&nbsp;
				<a href="#">上一页</a>&nbsp;&nbsp;
				<a href="#">下一页</a>&nbsp;&nbsp;
				<span></span>/<span></span>&nbsp;&nbsp;
				<a href="#">最后一页</a>
			</div>
		</div>
	</div>
</body>
</html>
