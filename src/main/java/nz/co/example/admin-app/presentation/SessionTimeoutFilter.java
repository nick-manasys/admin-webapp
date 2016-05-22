package nz.co.example.dev.presentation;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Nick
 * 
 * @see https
 *      ://www.dev.example.co.nz/amserver/UI/Login?realm=devadmin&IDToken0=LOGIN&goto=http://localhost:8080/dev
 *      -admin/listCircuits&gotoOnFail=http://localhost:8080/admin-app/login.jsp?error=login&IDToken1=<username>
 *      &IDToken2=<password>
 * 
 * @see https
 *      ://www.dev.example.co.nz/amserver/UI/Logout?goto=http%3A%2F%2Flocalhost%3A8080%2Fadmin-app%2FlistCircuits
 *      &gotoOnFail=http%3A%2F%2Flocalhost%3A8080%2Fadmin-app%2Flogin.jsp%3Ferror%3Dlogin&IDToken1=nick&IDToken2=nick
 */
public class SessionTimeoutFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(SessionTimeoutFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("SessionTimeoutFilter.init()");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        boolean loggedIn = session != null && ((HttpServletRequest) request).getSession().getAttribute("user") != null;
        // don't create if it doesn't exist
        if (loggedIn && !session.isNew()) {
            chain.doFilter(request, response);
        } else {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        logger.info("SessionTimeoutFilter.destroy()");
    }
}
