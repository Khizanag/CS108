package Controller;

import Model.AccountManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registration")
public class UserSystemRegistrationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = (String) request.getParameter("username");
        String password = (String ) request.getParameter("password");
        AccountManager manager = (AccountManager) getServletContext().getAttribute("AccountManager");

        request.setAttribute("username", username);

        if(manager.isUserNameHeld(username)) {
            request.getRequestDispatcher("/AccountInUsePage.jsp").forward(request, response);
        } else {
            manager.register(username, password);
            request.getRequestDispatcher("/UserWelcomePage.jsp").forward(request, response);
        }
    }
}
