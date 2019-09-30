
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<title>海文 在线短信平台</title>
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/sms.css" />
	<script type="text/javascript" src="scripts/jquery.js"></script>
</head>
<body>
	<%--提交到MesServlet--%>
	<form action="${pageContext.request.contextPath}/mesS/submit" method="post">
		<div id="main">
			<div class="mainbox">
				<%--导入开头--%>
				<%@ include file="title.jsp"%>
				<div class="content">
					<div class="message">

						<div class="tmenu">
							<ul class="clearfix">
								<li>发送给： <select name="toUser">
									<%--设置发送人--%>
									<c:choose>
										<%--如果传过来的是集合，就是发送信息--%>
										<c:when test="${!empty userList}">
											<c:forEach items="${requestScope.userList}" var="user">
												<option>${user.name}</option>
											</c:forEach>
										</c:when>
										<%--如果传过来的是对象，就是回信--%>
										<c:when test="${!empty sendUser}">
											<option>${requestScope.sendUser.name}</option>
										</c:when>
									</c:choose>
								</select>
								</li>
								<li>标题：<input type="text" name="title" id="title" /></li>
							</ul>
						</div>
						<div class="view">
							<textarea name="content" id="content"></textarea>
							<div class="send">
								<input type="submit" name="submit" value=" " />
							</div>
						</div>

					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
