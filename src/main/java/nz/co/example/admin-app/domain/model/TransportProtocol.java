/**
 * 
 */
package nz.co.example.dev.domain.model;

/**
 * The configured Transport Protocol allowed values and the associated display/config values.
 * 
 * @author nivanov
 */
public enum TransportProtocol {
    UDP, TCP;

    @Override
    public String toString() {
        return String.format("transport-protocol %s", name());
    }
}
