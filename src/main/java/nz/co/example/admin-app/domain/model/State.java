/**
 * 
 */
package nz.co.example.dev.domain.model;

/**
 * The configured state allowed values and the associated display/config values.
 * 
 * @author nivanov
 */
public enum State {
    ENABLED("enabled"), DISABLED("disabled");

    private String displayValue;

    private State(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    @Override
    public String toString() {
        return String.format("state %s", getDisplayValue());
    }
}
