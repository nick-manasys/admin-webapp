/**
 * 
 */
package nz.co.example.dev.domain.model;

import java.io.Serializable;
import java.net.InetAddress;

import com.google.common.base.Objects;

/**
 * The Sip Interface.
 * 
 * This encapsulates the configured values for a:
 * 
 * session-router->sip-interface-sip-port sub-element within the dev.
 * 
 * @author nivanov
 */
public final class SipPort implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -6338282264822093466L;

    private InetAddress address;
    private int port;
    private AnonymousMode anonMode;
    private TransportProtocol transportProtocol;

    public SipPort(InetAddress address, int port, AnonymousMode anonMode, TransportProtocol transportProtocol) {
        super();
        this.address = address;
        this.port = port;
        this.anonMode = anonMode;
        this.transportProtocol = transportProtocol;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(address, anonMode, port, transportProtocol);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof SipPort)) {
            return false;
        }
        SipPort other = (SipPort) obj;
        return Objects.equal(address, other.getAddress()) && Objects.equal(anonMode, other.getAnonMode())
                && Objects.equal(port, other.getPort())
                && Objects.equal(transportProtocol, other.getTransportProtocol());
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public AnonymousMode getAnonMode() {
        return anonMode;
    }

    public void setAnonMode(AnonymousMode anonMode) {
        this.anonMode = anonMode;
    }

    public TransportProtocol getTransportProtocol() {
        return transportProtocol;
    }

    public void setTransportProtocol(TransportProtocol transportProtocol) {
        this.transportProtocol = transportProtocol;
    }

    @Override
    public String toString() {
        return String.format("sip-port%naddress %s%nport %s%n%s%nallow-anonymous %s%n", address.getHostAddress(), port,
                transportProtocol, anonMode.getDisplayValue());
    }
}
