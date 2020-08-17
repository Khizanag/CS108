package Controller;

import Model.Product;
import Model.ProductManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Controller.ProductServlet", urlPatterns = "/Product")
public class ProductServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String productid = request.getParameter("productid");
        Product product = ProductManager.getProduct(productid);

        request.setAttribute("productid", product.getProductID());
        request.setAttribute("name", product.getName());
        request.setAttribute("imagefile", product.getImagefile());
        request.setAttribute("price", product.getPrice());
        request.getSession().setAttribute("product", product);

        request.getRequestDispatcher("/ProductPage.jsp").forward(request, response);
    }
}
