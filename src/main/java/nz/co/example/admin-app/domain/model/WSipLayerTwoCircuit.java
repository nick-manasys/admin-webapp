package nz.co.example.dev.domain.model;

import static com.google.common.base.Objects.equal;

import com.google.common.base.Objects;

/**
 * The collection of sub components that constitute a W-SIP Layer 2 Circuit.
 * 
 * A {@link WSipLayerTwoCircuit} consists of:
 * 
 * A {@link NetworkInterface}<br/>
 * A primary {@link AccessRealm}<br/>
 * A secondary {@link AccessRealm}<br/>
 * A primary {@link SipInterface}<br/>
 * A secondary {@link SipInterface}<br/>
 * A {@link SteeringPool}<br/>
 * 
 * @author nivanov
 * 
 */
public final class WSipLayerTwoCircuit implements Circuit {

    /**
     * 
     */
    private static final long serialVersionUID = -8212888608049132553L;

    private NetworkInterface networkInterface;
    private AccessRealm primaryAccessRealm;
    private AccessRealm secondaryAccessRealm;
    private SipInterface primarySipInterface;
    private SipInterface secondarySipInterface;
    private SteeringPool steeringPool;
    private String region;

    public WSipLayerTwoCircuit(NetworkInterface networkInterface, AccessRealm primaryAccessRealm,
            AccessRealm secondaryAccessRealm, SipInterface primarySipInterface, SipInterface secondarySipInterface,
            SteeringPool steeringPool, String region) {
        super();
        this.networkInterface = networkInterface;
        this.primaryAccessRealm = primaryAccessRealm;
        this.secondaryAccessRealm = secondaryAccessRealm;
        this.primarySipInterface = primarySipInterface;
        this.secondarySipInterface = secondarySipInterface;
        this.steeringPool = steeringPool;
        this.region = region;
    }

    public String getHint() {
        return "hint"; // toString().replaceAll("\"", "'");
    }

    public NetworkInterface getNetworkInterface() {
        return networkInterface;
    }

    public AccessRealm getPrimaryAccessRealm() {
        return primaryAccessRealm;
    }

    public AccessRealm getSecondaryAccessRealm() {
        return secondaryAccessRealm;
    }

    public SipInterface getPrimarySipInterface() {
        return primarySipInterface;
    }

    public SipInterface getSecondarySipInterface() {
        return secondarySipInterface;
    }

    public SteeringPool getSteeringPool() {
        return steeringPool;
    }

    @Override
    public String toString() {
        String result = "";
        try {
            result = this.getKey()
                    + "\n"
                    + String.format(
                            "%nNetwork Interface %n%n%s%n%nAccess Realm %n%n%s%n%nAccess Realm - NAT %n%n%s%n%nAccess SIP-Interface%n%n%s%n%nAccess SIP-Interface - NAT %n%n%s%n%nAccess Steering Pool %n%n%s",
                            networkInterface, primaryAccessRealm, secondaryAccessRealm, primarySipInterface,
                            secondarySipInterface, steeringPool);
        } catch (Throwable e) {
            result = e.getMessage();
        }
        return result;
    }

    @Override
    public String getCarrierId() {
        String result;
        String realmId = primaryAccessRealm.getIdentifier();
        int indexOfAfterFirstDash = realmId.indexOf("-") + 1;
        int indexOfSecondDash = realmId.indexOf("-", indexOfAfterFirstDash);
        result = realmId.substring(indexOfAfterFirstDash, indexOfSecondDash);
        return result;
    }

    @Override
    public String getIpAddress() {
        return networkInterface.getIpAddress().getHostAddress();
    }

    @Override
    public String getVLAN() {
        return String.valueOf(networkInterface.getSubport());
    }

    @Override
    public String getRegion() {
        return region;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(region, networkInterface, primaryAccessRealm, primarySipInterface,
                secondaryAccessRealm, secondarySipInterface, steeringPool);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof WSipLayerTwoCircuit)) {
            return false;
        }
        WSipLayerTwoCircuit other = (WSipLayerTwoCircuit) obj;
        return equal(this.getRegion(), other.getRegion())
                && equal(this.getNetworkInterface(), other.getNetworkInterface())
                && equal(this.getPrimaryAccessRealm(), other.getPrimaryAccessRealm())
                && equal(this.getPrimarySipInterface(), other.getPrimarySipInterface())
                && equal(this.getSecondaryAccessRealm(), other.getSecondaryAccessRealm())
                && equal(this.getSecondarySipInterface(), other.getSecondarySipInterface())
                && equal(this.getSteeringPool(), other.getSteeringPool());
    }

    @Override
    public CircuitType getCircuitType() {
        return CircuitType.W_SIP_LAYER_TWO;
    }

    @Override
    public String getKey() {
        return this.getRegion() + "-" + this.getVLAN();
    }
}
