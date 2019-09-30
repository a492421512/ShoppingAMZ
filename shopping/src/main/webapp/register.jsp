<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>亚马逊 - 注册页</title>
<link href="${pageContext.request.contextPath}/css/index.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/adv.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/scripts/jquery-2.1.0.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/scripts/adv.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/function.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/index.js"></script>
	<script>
		var $username,$pwd,$confirm,$sex,$birthday,$identity,$email,$mobile,$address,$code,$submit;
		$(function () {
			$username = $(".username");
            $pwd = $(".pwd");
            $confirm = $(".confirm");
            $sex = $(".sex");
            $birthday = $(".birthday");
            $identity = $(".identity");
            $email = $(".email");
            $mobile = $(".mobile");
            $address = $(".address");
            $code = $(".code");
            $submit = $(".submit");

            //绑定失去焦点事件
            $username.blur(function () {
				unameBlur();
            });

            //绑定单击事件
			$submit.click(function () {
				register();
            })
        });

		//检测用户名是否存在
		function unameBlur() {
			$.post("${pageContext.request.contextPath}/user",{"param":"nameBlur","username":$username.val()},function (data) {

			    if (data.success1){
                    $("#error").html(data.error);
				} else {
                    $("#error").html(data.error);
				}
            },"json")
        }

        //提交检测
		function register() {

			$.post("${pageContext.request.contextPath}/user/register",{
			    "uname":$username.val(),
				"pwd":$pwd.val(),
				"confirm":$confirm.val(),
				"idcard":$identity.val(),
				"sex":$sex.val(),
				"birthday":$birthday.val(),
				"email":$email.val(),
				"mobile":$mobile.val(),
				"address":$address.val(),
				"code":$code.val()},function (data) {
			    alert(JSON.stringify(data))
					//判断注册是否正确
					if (data.success){
						window.location="${pageContext.request.contextPath}/reg-result.jsp";
					}else{
						$("#error").html(data.error);
					}
            },"json")
        }
	</script>
</head>
<body>
<%@ include file="index_top.jsp"  %>

<div id="register" class="wrap">
	<div class="shadow">
		<em class="corner lb"></em>
		<em class="corner rt"></em>
		<div class="box">
			<h1>欢迎注册亚马逊</h1>
			<ul class="steps clearfix">
				<li class="current"><em></em>填写注册信息</li>
				<li class="last"><em></em>注册成功</li>
			</ul>
			<form id="regForm" method="post" action="#" onsubmit="return checkForm(this)">
				<table>
					<div id="error" style="font-size: 20px;color: red;text-align: center"></div>
					<tr>
						<td class="field">用户名：</td>
						<td><input id="userName" class="userName" type="text" name="userName" maxlength="15"/><span id="uName"></span></td>
					</tr>
					<tr>
						<td class="field">登录密码：</td>
						<td><input class="pwd" type="password" id="passWord" name="passWord"  /><span></span></td>
					</tr>
					<tr>
						<td class="field">确认密码：</td>
						<td><input class="confirm" type="password" name="rePassWord"  /><span></span></td>
					</tr>
					<tr>
						<td class="field">性别：</td>
						<td ><input class="sex" type="radio" name="sex"  style="border:0px;" checked="checked" value="0" />男<input type="radio" name="sex" style="border:0px;" value="1"/>女<span></span></td>
					</tr>
					<tr>
						<td class="field">出生日期：</td>
						<td><input class="birthday" type="text" name="birthday"  /><span></span></td>
					</tr>
					<tr>
						<td class="field">身份证：</td>
						<td><input class="identity" type="text" name="identity"  /><span></span></td>
					</tr>
					<tr>
						<td class="field">电子邮件：</td>
						<td><input class="email" type="text" name="email"  /><span id="uemail"></span></td>
					</tr>
					<tr>
						<td class="field">手机：</td>
						<td><input class="mobile" type="text" name="mobile"  /><span></span></td>
					</tr>
					<tr>
						<td class="field">地址：</td>
						<td><input class="address" type="text" name="address"/><span></span></td>
					</tr>
					<tr>
						<td class="field">验证码：</td>
						<td><input type="text" name="code" class="code"/>
							<img src="${pageContext.request.contextPath}/code" id="imgCode"><%--<a href="#" id="refresh">换一张</a>--%>
						</td>
					</tr>
					<tr>
						<td></td>
						<td><label class="ui-green"><input type="button" name="submit" value="提交注册" class="submit"/></label></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div class="clear"></div>
</div>
<div id="footer">
	Copyright &copy; 2018 上海海文 All Rights Reserved. 京ICP证1000001号
</div>
</body>
</html>

