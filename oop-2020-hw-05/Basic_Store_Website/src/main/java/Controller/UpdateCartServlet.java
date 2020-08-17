package Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "UpdateCartServlet", urlPatterns = "/UpdateCartServlet")
public class UpdateCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Session mysession = (Session) request.getSession().getAttribute("session");

        mysession.updateCart(request);

        double totalCost = mysession.getTotalCost();
        request.setAttribute("totalCost", totalCost);

        request.getRequestDispatcher("/ShoppingCartPage.jsp").forward(request, response);
    }
}
