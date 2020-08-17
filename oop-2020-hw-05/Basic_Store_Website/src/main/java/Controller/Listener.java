package Controller;

import Model.DatabaseConfig;
import Model.DatabaseConnector;
import Model.ProductManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;

@WebListener
public class Listener implements ServletContextListener, DatabaseConfig {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        Connection connection = DatabaseConnector.getInstance();
        context.setAttribute("connection", connection);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DatabaseConnector.closeConnection();
    }
}
