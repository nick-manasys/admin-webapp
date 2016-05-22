/**
 * 
 */
package nz.co.example.dev.domain.model;

/**
 * The configured trust level allowed values and the associated display/config values.
 * 
 * @author nivanov
 */
public enum TrustLevel {

    NONE("none"), LOW("low"), MEDIUM("medium"), HIGH("high");

    private String displayValue;

    private TrustLevel(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    @Override
    public String toString() {
        return String.format("trust-level %s", getDisplayValue());
    }
}
