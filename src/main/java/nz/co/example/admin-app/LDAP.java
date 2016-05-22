package nz.co.example.dev;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class LDAP {
    private static String contextFactory = "com.sun.jndi.ldap.LdapCtxFactory";

    private static String connectionTimeout = "5000";

    private static String protocol = "http";

    private static String authentication = "simple";

    /**
     * Create our directory context configuration.
     * 
     * @return java.util.Hashtable the configuration for the directory context.
     */
    private static Hashtable<String, String> getDirectoryContextEnvironment() {
        Hashtable<String, String> env = new Hashtable<String, String>();

        // Configure our directory context environment.
        // if (containerLog.isDebugEnabled() && connectionAttempt == 0)
        // containerLog.debug("Connecting to URL " + connectionURL);
        // else if (containerLog.isDebugEnabled() && connectionAttempt > 0)
        // containerLog.debug("Connecting to URL " + alternateURL);
        env.put(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
        // if (connectionName != null)
        // env.put(Context.SECURITY_PRINCIPAL, connectionName);
        // if (connectionPassword != null)
        // env.put(Context.SECURITY_CREDENTIALS, connectionPassword);
        // if (connectionURL != null && connectionAttempt == 0)
        // env.put(Context.PROVIDER_URL, connectionURL);
        // else if (alternateURL != null && connectionAttempt > 0)
        // env.put(Context.PROVIDER_URL, alternateURL);
        if (authentication != null)
            env.put(Context.SECURITY_AUTHENTICATION, authentication);
        if (protocol != null)
            env.put(Context.SECURITY_PROTOCOL, protocol);
        // if (referrals != null)
        // env.put(Context.REFERRAL, referrals);
        // if (derefAliases != null)
        // env.put(LdapAuthRealm.DEREF_ALIASES, derefAliases);
        if (connectionTimeout != null)
            env.put("com.sun.jndi.ldap.connect.timeout", connectionTimeout);

        return env;
    }

    /**
     * @param username
     * @return
     */
    public static String mapUsername(String username) {
        String result = "cn={:username},ou=Users,o=AUTH_VAULT";
        result = result.replaceAll(":username", username);
        return result;
    }

    public static boolean authenticate(String username, String password) {
        try {
            // Set up the environment for creating the initial context
            Hashtable<String, String> env = new Hashtable<String, String>();
            env = getDirectoryContextEnvironment();

            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, "ldap://10.200.203.116:389");
            //
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            // username, we have 2 \\ because one escapes the other
            env.put(Context.SECURITY_PRINCIPAL, mapUsername("a\\\\" + username));
            // password
            env.put(Context.SECURITY_CREDENTIALS, password);

            // Create the initial context
            DirContext ctx = new InitialDirContext(env);
            boolean result = ctx != null;

            if (ctx != null)
                ctx.close();

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        boolean result = authenticate("nivanov", "Asdf123#");
        System.out.println(result);
    }
}