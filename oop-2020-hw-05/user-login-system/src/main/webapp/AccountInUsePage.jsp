<%--
  Created by IntelliJ IDEA.
  User: gigakhizanishvili
  Date: 7/4/20
  Time: 21:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Account</title>
</head>
<body>
    <h1>The Name ${username} is <br>Already In Use</h1>

    <p>Please enter another name and password</p>
    <br>

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
