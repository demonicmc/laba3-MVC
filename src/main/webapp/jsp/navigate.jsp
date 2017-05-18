<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 27.04.17
  Time: 22:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Навигация</title>
    <style>
        <%@include file="/css/style.css" %>
    </style>


</head>
<body>

<fieldset class="right">
    <%--<c:url var="logoutUrl" value="${pageContext.request.contextPath}/logout" />--%>
    <%--<form action="${logoutUrl}" method="get" id="logoutForm" style="display:table-cell;vertical-align:center;">--%>
        <%--<a  tabindex="1" href="javascript:document.getElementById('logoutForm').submit()">Выйти</a>--%>
    <%--</form>--%>
        <a href="<c:url value="/logout" />"> Выйти</a>

</fieldset>

</body>
</html>
