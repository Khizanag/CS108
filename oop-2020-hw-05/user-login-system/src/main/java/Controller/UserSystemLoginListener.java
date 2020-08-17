package Controller;

import Model.AccountManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class UserSystemLoginListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        AccountManager manager = new AccountManager();
        sc.setAttribute("AccountManager", manager);
    }

    @Override
    public void contextDestroyed(ServletContextEvent unused) { }
}
