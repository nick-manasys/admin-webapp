package nz.co.example.dev.mvc;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Nick
 * 
 *         Servlet context listener for dev.
 */
public class devServletContextListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(devServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent arg) {
        logger.info("devServletContextListener.init()");
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg) {
        logger.info("devServletContextListener desttroyed()");
        // called when user logs out or session ends
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(null);
    }
}
