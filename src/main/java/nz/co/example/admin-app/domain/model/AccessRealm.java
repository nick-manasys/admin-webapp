/**
 * 
 */
package nz.co.example.dev.domain.model;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import nz.co.example.dev.integration.calls.WSConfigElementUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acmepacket.ems.ws.service.userobjects.WSConfigAttribute;
import com.acmepacket.ems.ws.service.userobjects.WSConfigElement;
import com.google.common.base.Objects;

/**
 * The Access Realm.
 * 
 * This encapsulates the configured values for an access realm.
 * 
 * <p>
 * <b>ACP</b>:<br/>
 * realmConfig<br/>
 * <b>ACLI</b>:<br/>
 * media-manager->realm-config sub-element within the dev.
 * </p>
 * 
 * @author nivanov
 */
public class AccessRealm implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -6091057763751659044L;

    private static final Logger logger = LoggerFactory.getLogger(AccessRealm.class);

    public static final String TYPE = "realmConfig";

    private static final String ADDRESS_PREFIX = "0.0.0.0";

    private String identifier;

    private String description;

    private InetAddress addressPrefix;

    private String networkInterfaces;

    private State mmInRealm = State.ENABLED;

    private State mmInNetwork = State.ENABLED;

    private State mmInSameIp = State.DISABLED;

    private State mmInSystem = State.ENABLED;

    private State bwCacNonMm = State.DISABLED;

    private State msmRelease = State.DISABLED;

    private TrustLevel accessControlTrustLevel = TrustLevel.MEDIUM;

    private int invalidSignalThreshold = 1;

    private int averageRateLimit = 0;

    private int maximumSignalThreshold = 4000;

    private int untrustedSignalThreshold = 0;

    private Options options = new Options("+sip-connect-pbx-reg=rewrite-new");

    private String parentRealm;

    private State symmetricLatching;

    private Order order;

    private String regionCode;

    public static AccessRealm createPrimary(String identifier, String description, String networkInterfaces,
            String parentRealm, String regionCode) {
        return new AccessRealm(identifier, description, networkInterfaces, parentRealm, Order.PRIMARY, regionCode);
    }

    public static AccessRealm createSecondary(String identifier, String description, String networkInterfaces,
            String parentRealm, String regionCode) {
        return new AccessRealm(identifier, description, networkInterfaces, parentRealm, Order.SECONDARY, State.ENABLED,
                regionCode);
    }

    private AccessRealm(String identifier, String description, String networkInterfaces, String parentRealm,
            Order order, String regionCode) {
        this();
        this.identifier = identifier;
        this.description = description;
        this.networkInterfaces = networkInterfaces;
        this.parentRealm = parentRealm;
        this.order = order;
        this.setRegionCode(regionCode);
    }

    private AccessRealm(String identifier, String description, String networkInterfaces, String parentRealm,
            Order order, State symmetricLatching, String regionCode) {
        this(identifier, description, networkInterfaces, parentRealm, order, regionCode);
        this.symmetricLatching = symmetricLatching;
    }

    private AccessRealm() {
        try {
            addressPrefix = InetAddress.getByName(ADDRESS_PREFIX);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public String getId() {
        return getRegionCode() + "-" + getIdentifier();

    }

    public String getIdentifier() {
        return identifier;
    }

    public String getDescription() {
        return description;
    }

    public InetAddress getAddressPrefix() {
        return addressPrefix;
    }

    public String getNetworkInterfaces() {
        return networkInterfaces;
    }

    public State getMmInRealm() {
        return mmInRealm;
    }

    public State getMmInNetwork() {
        return mmInNetwork;
    }

    public State getMmInSameIp() {
        return mmInSameIp;
    }

    public State getMmInSystem() {
        return mmInSystem;
    }

    public State getBwCacNonMm() {
        return bwCacNonMm;
    }

    public State getMsmRelease() {
        return msmRelease;
    }

    public TrustLevel getAccessControlTrustLevel() {
        return accessControlTrustLevel;
    }

    public int getInvalidSignalThreshold() {
        return invalidSignalThreshold;
    }

    public int getAverageRateLimit() {
        return averageRateLimit;
    }

    public int getMaximumSignalThreshold() {
        return maximumSignalThreshold;
    }

    public int getUntrustedSignalThreshold() {
        return untrustedSignalThreshold;
    }

    public Options getOptions() {
        return options;
    }

    public String getParentRealm() {
        return parentRealm;
    }

    public State getSymmetricLatching() {
        return symmetricLatching;
    }

    public Order getOrder() {
        return order;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AccessRealm)) {
            return false;
        }
        AccessRealm other = (AccessRealm) obj;

        return Objects.equal(accessControlTrustLevel, other.getAccessControlTrustLevel())
                && Objects.equal(addressPrefix, other.getAddressPrefix())
                && Objects.equal(averageRateLimit, other.getAverageRateLimit())
                && Objects.equal(bwCacNonMm, other.getBwCacNonMm())
                && Objects.equal(description, other.getDescription())
                && Objects.equal(identifier, other.getIdentifier())
                && Objects.equal(invalidSignalThreshold, other.getInvalidSignalThreshold())
                && Objects.equal(maximumSignalThreshold, other.getMaximumSignalThreshold())
                && Objects.equal(mmInNetwork, other.getMmInNetwork()) && Objects.equal(mmInRealm, other.getMmInRealm())
                && Objects.equal(mmInSameIp, other.getMmInSameIp()) && Objects.equal(mmInSystem, other.getMmInSystem())
                && Objects.equal(msmRelease, other.getMsmRelease())
                && Objects.equal(networkInterfaces, other.getNetworkInterfaces())
                && Objects.equal(options, other.getOptions()) && Objects.equal(order, other.getOrder())
                && Objects.equal(parentRealm, other.getParentRealm())
                && Objects.equal(symmetricLatching, other.getSymmetricLatching())
                && Objects.equal(untrustedSignalThreshold, other.getUntrustedSignalThreshold());
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(accessControlTrustLevel, addressPrefix, averageRateLimit, bwCacNonMm, description,
                identifier, invalidSignalThreshold, maximumSignalThreshold, mmInNetwork, mmInRealm, mmInSameIp,
                mmInSystem, msmRelease, networkInterfaces, options, order, parentRealm, symmetricLatching,
                untrustedSignalThreshold);
    }

    @Override
    public String toString() {
        switch (order) {
        case PRIMARY:
            return String
                    .format("media-manager%nrealm-config%nidentifier %s%ndescription \"%s\"%naddr-prefix %s%nnetwork-interfaces %s%nmm-in-realm %s%nmm-in-network %s%nmm-same-ip %s%nmm-in-system %s%nbw-cac-non-mm %s%nmsm-release %s%naccess-control-trust-level %s%ninvalid-signal-threshold %s%naverage-rate-limit %s%nmaximum-signal-threshold %s%nuntrusted-signal-threshold %s%n%sparent-realm %s",
                            identifier, description, addressPrefix.getHostAddress(), networkInterfaces,
                            mmInRealm.getDisplayValue(), mmInNetwork.getDisplayValue(), mmInSameIp.getDisplayValue(),
                            mmInSystem.getDisplayValue(), bwCacNonMm.getDisplayValue(), msmRelease.getDisplayValue(),
                            accessControlTrustLevel.getDisplayValue(), invalidSignalThreshold, averageRateLimit,
                            maximumSignalThreshold, untrustedSignalThreshold, options, parentRealm);
        case SECONDARY:
            return String
                    .format("media-manager%nrealm-config%nidentifier %s%ndescription \"%s\"%naddr-prefix %s%nnetwork-interfaces %s%nmm-in-realm %s%nmm-in-network %s%nmm-same-ip %s%nmm-in-system %s%nbw-cac-non-mm %s%nmsm-release %s%naccess-control-trust-level %s%ninvalid-signal-threshold %s%naverage-rate-limit %s%nmaximum-signal-threshold %s%nuntrusted-signal-threshold %s%n%sparent-realm %s%nsymmetric-latching %s",
                            identifier, description, addressPrefix.getHostAddress(), networkInterfaces,
                            mmInRealm.getDisplayValue(), mmInNetwork.getDisplayValue(), mmInSameIp.getDisplayValue(),
                            mmInSystem.getDisplayValue(), bwCacNonMm.getDisplayValue(), msmRelease.getDisplayValue(),
                            accessControlTrustLevel.getDisplayValue(), invalidSignalThreshold, averageRateLimit,
                            maximumSignalThreshold, untrustedSignalThreshold, options, parentRealm,
                            symmetricLatching.getDisplayValue());
        default:
            return String.format("Not a correctly configured primary or secondary AccessRealm id: %s", identifier);
        }
    }

    /**
     * @return
     */
    public WSConfigElement toWSConfigElement() {
        WSConfigElement result = new WSConfigElement();
        ArrayList<WSConfigElement> subElements = new ArrayList<WSConfigElement>();
        // attributes
        result.setElementTypePath(TYPE);
        result.setType(TYPE);

        ArrayList<WSConfigAttribute> attributeList = new ArrayList<WSConfigAttribute>();
        attributeList.add(WSConfigElementUtility.createAttribute("id", this.getIdentifier()));
        attributeList.add(WSConfigElementUtility.createAttribute("description", this.getDescription()));
        attributeList.add(WSConfigElementUtility.createAttribute("parent", this.getParentRealm()));
        result.setAttributeList(attributeList);

        WSConfigElement childElement = new WSConfigElement();
        childElement.setElementTypePath(TYPE + "/networkInterfaceId");
        childElement.setType(TYPE + "/networkInterfaceId");

        attributeList = new ArrayList<WSConfigAttribute>();
        String name = this.getNetworkInterfaces().substring(0, this.getNetworkInterfaces().indexOf(":"));
        String subPortId = this.getNetworkInterfaces().substring(this.getNetworkInterfaces().indexOf(":") + 1,
                this.getNetworkInterfaces().length());
        WSConfigAttribute attribute = WSConfigElementUtility.createAttribute("name", name);
        attributeList.add(attribute);
        attribute = WSConfigElementUtility.createAttribute("subPortId", subPortId);
        attributeList.add(attribute);
        childElement.setAttributeList(attributeList);

        subElements.add(childElement);

        logger.debug("wsConfig=" + result);
        if (null != subElements) {
            result.setChildren(subElements);
        }
        return result;
    }

    /**
     * @param element
     * @return
     */
    public static AccessRealm fromWSConfigElement(WSConfigElement fullElement) {
        AccessRealm result = null;
        String identifier;
        String description;
        List<NetworkInterface> networkInterfaces;
        String parentRealm;
        String regionCode;

        identifier = WSConfigElementUtility.getAttributeByName(fullElement, "id");
        description = WSConfigElementUtility.getAttributeByName(fullElement, "description");
        networkInterfaces = WSConfigElementUtility.getChildrenByType(fullElement, TYPE + "/networkInterfaceId");
        parentRealm = WSConfigElementUtility.getAttributeByName(fullElement, "parent");
        // FIXME
        regionCode = WSConfigElementUtility.getAttributeByName(fullElement, "");

        String networkInterfacesString = networkInterfaces.get(0).getName() + ":"
                + networkInterfaces.get(0).getSubport();
        if (!WSConfigElementUtility.getAttributeByName(fullElement, "id").endsWith("n.acc")) {
            result = AccessRealm.createPrimary(identifier, description, networkInterfacesString, parentRealm,
                    regionCode);
        } else {
            result = AccessRealm.createSecondary(identifier, description, networkInterfacesString, parentRealm,
                    regionCode);
        }
        return result;
    }
}
