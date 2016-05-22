package nz.co.example.dev.presentation;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copies the SSO credentials from the request headers into the session.
 * <p>
 * A cut down version of the original SelfServiceRequestFilter filter with only the credentials transfer from request
 * parameters to session and the general error handling.
 * </p>
 */
public class SSOCredentialsFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(SSOCredentialsFilter.class);

    private static final String INIT_PARAM_ERROR_PAGE = "application.errorPage";

    private String errorPage;

    public SSOCredentialsFilter() {
    }

    public void init(FilterConfig config) throws ServletException {
        errorPage = config.getInitParameter(INIT_PARAM_ERROR_PAGE);
    }

    /**
   * 
   */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {

        copyAMRequestParamsToSession((HttpServletRequest) request);

        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            if (request instanceof HttpServletRequest) {
                HttpServletRequest httpRequest = (HttpServletRequest) request;
                httpRequest.getSession().setAttribute("exception", e);
            }
            if (response instanceof HttpServletResponse) {
                logger.error("Uncaught exception encountered", e);
                String contextPath = ((HttpServletRequest) request).getContextPath();
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.sendRedirect(httpResponse.encodeRedirectURL(contextPath + errorPage));
                return;
            }
            // displays error in browser
            // throw new ServletException(e);
        }
    }

    /**
     * Checks if the Access Manager parameters are on session. If not, they are
     * retrieved from the request headers.
     */
    private void copyAMRequestParamsToSession(HttpServletRequest req) {

        String uri = req.getRequestURI();
        String credType = req.getHeader(SessionKeys.CREDENTIALTYPE);

        if (credType == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("SSOCredentialsFilter no headers... " + uri);
            }
            HttpSession session = req.getSession(true);
            session.setAttribute(SessionKeys.USERNAME, "username");
            session.setAttribute(SessionKeys.PERSONID, "header(PERSONID)" + req.getHeader(SessionKeys.PERSONID));
        } else {
            // set or refresh the credentials
            String username = req.getHeader(SessionKeys.USERNAME);
            if (logger.isDebugEnabled()) {
                logger.debug("SSOCredentialsFilter [" + credType + "][" + username + "] at " + uri);
            }
            HttpSession session = req.getSession(true);
            session.setAttribute(SessionKeys.CREDENTIALTYPE, credType);
            session.setAttribute(SessionKeys.USERNAME, username);
            session.setAttribute(SessionKeys.PERSONID, req.getHeader(SessionKeys.PERSONID));
            session.setAttribute(SessionKeys.FIRSTNAME, req.getHeader(SessionKeys.FIRSTNAME));
            session.setAttribute(SessionKeys.LASTNAME, req.getHeader(SessionKeys.LASTNAME));
            session.setAttribute(SessionKeys.CCR_ROLE, req.getHeader(SessionKeys.CCR_ROLE));
            session.setAttribute(SessionKeys.ACCOUNT_NUMBER, req.getHeader(SessionKeys.ACCOUNT_NUMBER));

            Enumeration<?> headerNames = req.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = (String) headerNames.nextElement();
                if (headerName.indexOf(SessionKeys.SUBSCRIPTION_ROLE) != -1) {
                    session.setAttribute(headerName, req.getHeader(headerName));
                }
            }
        }
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

}
