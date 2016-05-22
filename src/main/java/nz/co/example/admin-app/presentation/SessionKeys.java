package nz.co.example.dev.presentation;

/**
 * This class contains keys which will be used to associate data that is put on HttpSession Use these keys to
 * add/retrieve variables from the session map.
 * 
 * @author Greg Harrison
 */
public final class SessionKeys {

    public static final String LOGIN_ID = "LOGIN_ID";
    public static final String LOGIN_ORIGIN = "LoginOrigin"; // Values can be Internet|LAN|Clarify
    public static final String SECURITY_QUESTION = "SECURITY_QUESTION";
    public static final String SECURITY_ANSWER = "SECURITY_ANSWER";
    public static final String PROFILE = "CURRENT_PROFILE";
    public static final String CREDENTIALTYPE = "credentials.type";
    public static final String USERNAME = "credentials.username";
    public static final String FIRSTNAME = "person.firstname";
    public static final String LASTNAME = "person.lastname";
    public static final String PERSONID = "person.id";
    public static final String CATEGORY = "category"; // Set by the web app, not AM, using NetworkDetermination().
                                                      // Values can be offnet|onnet
    public static final String CCR_ROLE = "ccr.role";
    public static final String SUBSCRIPTION_ROLE = "subscriptionrole";

    public static final String ACCOUNT_NUMBER = "account_no"; // needed ?
    // org=tclenterprise&service=ccr&account_no=[account#]&extended_user=[ccr_id]&extended_password=test

    public static final String REGISTRATION_COMPLETION_MSG = "registration.completion.msg";
    public static final String RESET_PASSWORD_ERROR = "reset.password.error";
    public static final String RESET_PASSWORD_INVALID_SECURITY_QUESTION_ANSWER = "invalid.security.question.answer";

    public static final String CREDTYPE_IDENTITY = "customerzoneID";
    public static final String CREDTYPE_EMAIL = "emailaddress"; // Used by AccessManager
    public static final String CREDTYPE_IDM_EMAIL = "Email";// Used by IdentityMAnager see natalia's email on 13-03-2008
                                                            // to Tim, Greg and Andrew
    public static final String CREDTYPE_ACCOUNT = "account";
    public static final String CREDTYPE_CCR = "CCR"; // Used for CCR logins from clarify

    // reCAPTCHA keys
    public static final String RECAPTCHA_CHALLENGE = "recaptcha_challenge_field";
    public static final String RECAPTCHA_RESPONSE = "recaptcha_response_field";
    public static final String CAPTCHA_ATTEMPTS = "captcha_attempts";

    public static final String REQUEST_ID = "request_id";

    private SessionKeys() {
        // no instances
    }
}
