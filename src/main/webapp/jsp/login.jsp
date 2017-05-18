<%--
  Created by IntelliJ IDEA.
  User: set
  Date: 23.04.17
  Time: 22:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page errorPage="error.jsp" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true"%>
<%--<%@ page import="com.laba3.dao.UserDao " %>--%>
<%--<%@ page import="com.laba3.dao.UserDaoImp" %>--%>
<%--<%@ page import="com.laba3.ConnectBase" %>--%>
<%--<%@ page import="com.laba3.pojo.User" %>--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Вход</title>
    <style>
        <%@include file="../css/input.css" %>
    </style>
</head>
<body>
<form action="${pageContext.request.contextPath}/j_spring_security_check"  method="post" accept-charset="utf-8">
    <div class="main">
        <div class="textbox">
            Введите логин:<input type="text" maxlength="20" name="login"
                                 required pattern="[A-Za-zА-Яа-яЁё]+$" title="Для заполнения используйте буквы" />
        </div>
        <div class="textbox">
            Введите пароль:<input type="password" minlength ="4" maxlength= "8"
                                  name="password" required pattern="[A-Za-zА-Яа-яЁё0-9]+$" />
        </div>
    </div>
    <input type="submit" name="input" value="Вход"  class="button" />
</form>
    <div class="main">
        <a href="${pageContext.request.contextPath}/registration"><input type="submit" name="registration" value="Регистрация" class="button"></a>
    </div>
</body>
</html>