package Controller;

import Model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Controller.ShoppingCartServlet", urlPatterns = "/ShoppingCartServlet")
public class ShoppingCartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Session session = (Session)request.getSession().getAttribute("session");
        Product productToAdd = (Product)request.getSession().getAttribute("product");
        session.addToCart(productToAdd);

        Session mysession = (Session)request.getSession().getAttribute("session");
        double totalCost = mysession.getTotalCost();
        request.setAttribute("totalCost", totalCost);

        request.getRequestDispatcher("/ShoppingCartPage.jsp").forward(request, response);
    }
}
