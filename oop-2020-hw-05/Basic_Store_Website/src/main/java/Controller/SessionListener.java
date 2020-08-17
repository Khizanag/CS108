package Controller;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        Session session = new Session(se.getSession());
        System.out.println("session created");
        se.getSession().setAttribute("session", session);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) { }
}
