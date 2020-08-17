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
    <title>Information Incorrect</title>

</head>
<body>

<h1>Please Try Again</h1>

<p>Either your name or password is incorrect. Please try again.</p>

<form action="login" method="GET">

    User Name:
    <input type="text" name="username">
    <br>
    Password:
    <input type="text" name="password">
    <input type="submit" value="Login">
    <br>

</form>

<a href="CreateAccountPage.jsp">Create New Account</a>

</body>
</html>
