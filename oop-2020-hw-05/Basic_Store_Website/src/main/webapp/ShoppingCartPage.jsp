<%@ page import="Model.Product" %>
<%@ page import="java.util.Map" %>
<%@ page import="Model.ProductManager" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="Controller.Session" %>
<%@ page import="java.net.http.HttpRequest" %>
<%@ page import="Controller.CartItem" %><%--
  Created by IntelliJ IDEA.
  User: gigakhizanishvili
  Date: 7/5/20
  Time: 07:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shopping Cart</title>
</head>
<body>

    <h1>Shopping Cart</h1>

    <%--    do list of orders --%>

    <form action="UpdateCartServlet" method="GET">

        <ul> <%

            Session mysession = (Session)request.getSession().getAttribute("session");
            Map<String, CartItem> products = mysession.getCartProducts();

            for (String productID : products.keySet()) {
                CartItem item = products.get(productID);
                Product product = item.getProduct();
                int count = item.getCount();
                String productid = product.getProductID();
                String name = product.getName(); %>
            <li>
                <input type="text" name="<%=productID%>" value=<%=count%>>
                <%=product.getName()%>, <%=product.getPrice()%>
            </li>
            <%  }  %>
        </ul>

        Total: $${totalCost} <input type="submit" value="Update Cart">
    </form>

    <a href="StudentStorePage.jsp"> Continue Shopping</a>

</body>
</html>
