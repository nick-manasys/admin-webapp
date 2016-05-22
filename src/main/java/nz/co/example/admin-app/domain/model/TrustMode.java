/**
 * 
 */
package nz.co.example.dev.domain.model;

/**
 * The configured trust mode allowed values and the associated display/config values.
 * 
 * @author nivanov
 */
public enum TrustMode {
    ALL("all"), AGENTS_ONLY("agents-only"), REALM_PREFIX("realm-prefix"), REGISTERED("registered");

    private String displayValue;

    private TrustMode(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    @Override
    public String toString() {
        return String.format("trust-mode %s", getDisplayValue());
    }
}
