<%--
  Created by IntelliJ IDEA.
  User: gigakhizanishvili
  Date: 7/5/20
  Time: 06:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${name}</title>
</head>
<body>
    <h1>${name}</h1>
    <img src = "store-images/${imagefile}">
    <br>
    <form action="ShoppingCartServlet" method="GET">
        $${price}<input type="submit" value="Add to Cart">
    </form>
</body>
</html>
