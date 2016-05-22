/**
 * 
 */
package nz.co.example.dev.domain.model;

/**
 * The configured contact mode allowed values and the associated display/config values.
 * 
 * @author nivanov
 */
public enum ContactMode {
    NONE("none"), MADDR("maddr"), STRICT("strict"), LOOSE("loose");

    private String displayValue;

    private ContactMode(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    @Override
    public String toString() {
        return String.format("contact-mode %s", getDisplayValue());
    }
}
