
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>短消息平台</title>
<link type="text/css" rel="stylesheet" href="css/sms.css" />
	<script type="text/javascript" src="scripts/jquery.js"></script>
</head>
<script>
    var $name,$pwd,$submit;
    //当页面加载完之后
    $(function () {
        $name = $(".name");
        $pwd = $(".pwd");
        $submit = $("#submit");

        //绑定鼠标单击事件
        $submit.click(function () {
            post();
        })
    });

    function post() {
        $.post("${pageContext.request.contextPath}/userS/login",{"name":$name.val(),"password":$pwd.val()},function (data) {
            //判断登陆是否成功
            var json = (data.success);
            if (json) {
                //如果成功就跳转页面
                window.location="${pageContext.request.contextPath}/main.jsp";//修改浏览器地址
            }else{ //如果输入的错误，就打印错误信息
                $("#error").html(data.info);
            }
        },"json")
    }
</script>
<body>
	<div id="loginTitle" class="png"></div>
	<div id="loginForm" class="userForm png">
			<dl>
				<div id="error"></div>
				<dt>用户名：</dt>
				<dd>
					<input type="text" class="name" />
				</dd>
				<dt>密 码：</dt>
				<dd>
					<input type="password" class="pwd" />
				</dd>
				
			</dl>
			<div class="buttons">
				<input class="btn-login png" type="button" id="submit" value=" " />
				<a href="register.jsp"><input class="btn-reg png" type="button" name="register" value=" " /></a>
			</div>
	</div>
	
	
</body>
</html>








