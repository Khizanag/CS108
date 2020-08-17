<%--
  Created by IntelliJ IDEA.
  User: gigakhizanishvili
  Date: 7/4/20
  Time: 21:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Account</title>
</head>
<body>

<h1>Create New Account</h1>

<p>Please enter proposed name and password</p>

<form action="registration" method="GET">

    User Name:
    <input type="text" name="username">
    <br>
    Password:
    <input type="text" name="password">
    <input type="submit" value="Login">

</form>

</body>
</html>
