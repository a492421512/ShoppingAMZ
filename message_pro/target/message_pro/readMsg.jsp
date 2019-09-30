
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<title>读短消息</title>
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/sms.css" />

</head>
<body>
	<div id="main">
		<div class="mainbox">
			<div class="title readMessage png"></div>
			<%--导入开头--%>
			<%@ include file="title.jsp"%>
			<div class="content">
				<div class="message">
					<div class="tmenu">
						<ul class="clearfix">
							<li>题目：${Message.title}</li>
							<li>来自：${Message.sendUser.name}</li>
							<li>${Message.msgCreateDate}</li>
						</ul>
					</div>
					<div class="view">
						<p>${Message.msgContent}</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
