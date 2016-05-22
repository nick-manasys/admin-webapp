/**
 * 
 */
package nz.co.example.dev.domain.model;

/**
 * The configured network address translation traversal mode allowed values and the associated display/config values.
 * 
 * @author nivanov
 */
public enum NatTraversal {
    NONE("none"), ALWAYS("always"), RPORT("rport");

    private String displayValue;

    private NatTraversal(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    @Override
    public String toString() {
        return String.format("nat-traversal %s", getDisplayValue());
    }
}
