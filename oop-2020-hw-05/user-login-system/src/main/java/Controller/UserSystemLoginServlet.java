package Controller;

import Model.AccountManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class UserSystemLoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = (String) request.getParameter("username");
        if(username == null) {
            request.getRequestDispatcher("/LogInPage.jsp").forward(request, response);
            return;
        }


        String password = (String ) request.getParameter("password");
        AccountManager manager = (AccountManager) getServletContext().getAttribute("AccountManager");

        if(manager.isCorrectLogin(username, password)) {
            request.setAttribute("username", username);
            request.getRequestDispatcher("/UserWelcomePage.jsp").forward(request, response);
        } else
            request.getRequestDispatcher("/IllegalLoginPage.jsp").forward(request, response);
    }
}
