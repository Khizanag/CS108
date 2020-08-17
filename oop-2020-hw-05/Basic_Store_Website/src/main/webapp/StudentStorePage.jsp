<%@ page import="java.util.Iterator,
                            Model.Product,
                            Model.ProductManager,
                            Model.DatabaseConnector" %><%--
  Created by IntelliJ IDEA.
  User: gigakhizanishvili
  Date: 7/5/20
  Time: 05:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Student Store</title>
</head>
<body>

    <h1>Student Store</h1>
    <p>Items avaible:</p>

    <ul> <%
        Iterator<Product> products = ProductManager.getProducts();
        while (products.hasNext()) {
            Product product = (Product) products.next();
            String productid = product.getProductID();
            String name = product.getName(); %>
        <li>
            <a href = "/Product?productid=<%=productid%>"> <%=name%> </a>
        </li>
        <%  }  %>
    </ul>

</body>
</html>
