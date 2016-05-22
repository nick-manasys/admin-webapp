/**
 * 
 */
package nz.co.example.dev.domain.model;

/**
 * The configured anonymous mode allowed values and the associated display/config values.
 * 
 * @author nivanov
 */
public enum AnonymousMode {
    ALL("all"), AGENTS_ONLY("agents-only"), REALM_PREFIX("realm-prefix"), REGISTERED("registered"), REGISTER_PREFIX(
            "register-prefix");

    private String displayValue;

    private AnonymousMode(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    @Override
    public String toString() {
        return String.format("anonymous-mode %s", getDisplayValue());
    }
}
