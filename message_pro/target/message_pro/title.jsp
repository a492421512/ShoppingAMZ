
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<div class="menu">
    <span>当前用户：<a href="${pageContext.request.contextPath}/main.jsp">${user.name}</a></span> <span><a
        href="${pageContext.request.contextPath}/userS/queryUserAll">发短消息</a></span> <span><a href="${pageContext.request.contextPath}/userS/exists">退出</a></span>
</div>
<script type="text/javascript" src="scripts/jquery.js"></script>

