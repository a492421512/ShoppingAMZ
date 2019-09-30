<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>亚马逊 - 留言</title>
	<link href="${pageContext.request.contextPath}/css/index.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/css/adv.css" rel="stylesheet" type="text/css"/>
	<script src="${pageContext.request.contextPath}/scripts/jquery-2.1.0.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/scripts/adv.js" type="text/javascript"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/function.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/index.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/comment.js"></script>
	<script>
        $(function () {
            querycom(1,2);
        });
        function querycom(currentPage,PageSize) {
            var $ul = $("#book");
            $.post("${pageContext.request.contextPath}/comment/queryCom",{
                "currentPage":currentPage,
                "pagesize":PageSize
            },function (data) {
                //alert(JSON.stringify(data))
                $ul.empty();
                for (var i = 0; i < data.list.length; i++) {
                    var $v = data.list[i];
                    var $li = $("<li></li>");
                    var $dl = $("<dl></dl>");
                    var $dt = $("<dt>内容："+$v.content+"</dt>");
                    var $dd1 = $("<dd class='author'><span>作者："+$v.nick_Name+"</span></dd>");
                    var $dd2 = $("<dd>评论时间："+$v.create_Time+"</dd>");
                    var $dd3 = $("<dd>回复："+$v.reply+"</dd>");
                    var $dd4 = $("<dd>回复时间："+$v.reply_Time+"</dd>");

                    $dl.append($dt);
                    $dl.append($dd1);
                    $dl.append($dd2);
                    $dl.append($dd3);
                    $dl.append($dd4);
                    $li.append($dl);
                    $ul.append($li);
                }

                $a = $("#btnLook>a");
                $a.eq(0).attr("href", "javascript:querycom('" + 1 + "','" + PageSize + "')");// showMsgs('1','5')
                $a.eq(1).attr("href", "javascript:querycom('" + data.prePage + "','" + PageSize + "')");
                $a.eq(2).attr("href", "javascript:querycom('" + data.nextPage + "','" + PageSize + "')");
                $a.eq(3).attr("href", "javascript:querycom('" + data.pages + "','" + PageSize + "')");
                $span = $("#btnLook>span");
                $span.eq(0).html(data.pageNum);
                $span.eq(1).html(data.pages);
            },"json");
        };
	</script>
</head>
<body>
<%@ include file="index_top.jsp"  %>
<div id="position" class="wrap">
	您现在的位置：<a href="${pageContext.request.contextPath}/index.jsp">亚马逊</a> &gt; 在线留言
</div>
<div id="main" class="wrap">
	<div class="lefter">
		<%@ include file="index_product_sort.jsp" %>
	</div>
	<div class="main" id="com">
		<div class="guestbook">
			<h2>全部留言</h2>
			<ul id="book">
				<%--<li>
					<dl>
						<dt>内容：</dt>
						<dd class="author"><span>作者：</span></dd>
						<dd>评论时间：</dd>
						<dd>回复：</dd>
						<dd>回复时间：</dd>
					</dl>
				</li>--%>
			</ul>
			<div align="center" style="margin-top:10px" id="btnLook">
				<a href="#">首页</a>&nbsp;&nbsp;&nbsp;
				<a href="#">上一页</a>&nbsp;&nbsp;
				<a href="#">下一页</a>&nbsp;&nbsp;
				<span></span>/<span></span>&nbsp;&nbsp;
				<a href="#">最后一页</a>
			</div>
			<div class="clear"></div>
			<div class="pager">
				<ul class="clearfix">
				</ul>
			</div>

			<div id="reply-box">
				<form action="${pageContext.request.contextPath}/comment/addCom" method="post" onsubmit="return commentCheck()">
					<table>
						<tr>
							<td class="field">昵称：</td>
							<td><input class="text" type="text" name="nick_Name" /></td>
						</tr>
						<tr>
							<td class="field">留言标题：</td>
							<td><input class="text" type="text" name="reply" /></td>
						</tr>
						<tr>
							<td class="field">留言内容：</td>
							<td><textarea name="content"></textarea></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><label class="ui-blue"><input type="submit" name="submit" value="提交留言" /></label></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
	<div class="clear"></div>
</div>
<div id="footer">
	Copyright &copy; 2018 上海海文 All Rights Reserved. 京ICP证1000001号
</div>
</body>
</html>
