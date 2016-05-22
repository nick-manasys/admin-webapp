package nz.co.example.dev.integration;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author nivanov
 * 
 */
public class ProxyAuthenticator extends Authenticator {
    Log logger = LogFactory.getLog(ProxyAuthenticator.class);

    private String username;

    private String password;

    public ProxyAuthenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        logger.debug("request recieved from " + getRequestingHost());
        return new PasswordAuthentication(username, password.toCharArray());
    }
}