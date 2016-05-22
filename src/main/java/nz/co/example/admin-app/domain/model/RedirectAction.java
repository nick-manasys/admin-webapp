/**
 * 
 */
package nz.co.example.dev.domain.model;

/**
 * The configured redirect action allowed values and the associated display/config values.
 * 
 * @author nivanov
 */
public enum RedirectAction {
    EMPTY("empty"), PROXY("proxy"), REDIRECT("redirect"), RECORD_ROUTE("record-route"), STATELESS("stateless");

    private String displayValue;

    private RedirectAction(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    @Override
    public String toString() {
        return String.format("redirect-action %s", getDisplayValue());
    }
}
