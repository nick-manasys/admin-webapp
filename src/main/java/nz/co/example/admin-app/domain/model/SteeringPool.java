/**
 * 
 */
package nz.co.example.dev.domain.model;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;

import nz.co.example.dev.integration.calls.WSConfigElementUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acmepacket.ems.ws.service.userobjects.WSConfigAttribute;
import com.acmepacket.ems.ws.service.userobjects.WSConfigElement;
import com.google.common.base.Objects;

/**
 * The Steering Pool.
 * 
 * This encapsulates the configured values for a:
 * 
 * media-manager->steering-pool sub-element within the dev.
 * 
 * @author nivanov
 */
public final class SteeringPool implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 3509890716533152195L;

    private static final Logger logger = LoggerFactory.getLogger(SteeringPool.class);

    private static final String ATTRIBUTE_SUB_PORT_ID = "subPortId";

    private static final String ATTRIBUTE_NAME = "name";

    private static final String ATTRIBUTE_END_PORT = "endPort";

    private static final String ATTRIBUTE_START_PORT = "startPort";

    private static final String ATTRIBUTE_IP_ADDRESS = "ipAddress";

    private static final String ATTRIBUTE_REALM_ID = "realmID";

    /**
     * Steering pool type identifier
     */
    private static final String TYPE = "steeringPool";

    /**
     * Access realm id
     */
    private String realmId;

    /**
     * 
     */
    private InetAddress ipAddress;

    /**
     * Start of port range
     */
    private int startPort;

    /**
     * End of port range
     */
    private int endPort;

    private String networkInterface;

    public static SteeringPool create(InetAddress ipAddress, String realmId, int startPort, int endPort) {
        return new SteeringPool(realmId, ipAddress, startPort, endPort);
    }

    public SteeringPool(String realmId, InetAddress ipAddress, int startPort, int endPort) {
        super();
        this.realmId = realmId;
        this.ipAddress = ipAddress;
        this.startPort = startPort;
        this.endPort = endPort;
        this.networkInterface = ":0";
    }

    public String getId() {
        return getIpAddress().getHostAddress() + "-" + getStartPort() + "-" + getEndPort() + "-" + getRealmId();
    }

    @Override
    public String toString() {
        return String.format("media-manager%nsteering-pool%nip-address %s%nstart-port %s%nend-port %s%nrealm-id %s%n",
                ipAddress.getHostAddress(), startPort, endPort, realmId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(endPort, ipAddress, realmId, startPort);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof SteeringPool)) {
            return false;
        }
        SteeringPool other = (SteeringPool) obj;
        return Objects.equal(endPort, other.getEndPort()) && Objects.equal(ipAddress, other.getIpAddress())
                && Objects.equal(realmId, other.getRealmId()) && Objects.equal(startPort, other.getStartPort());
    }

    public String getRealmId() {
        return realmId;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public int getStartPort() {
        return startPort;
    }

    public int getEndPort() {
        return endPort;
    }

    private void setNetworkInterface(String networkInterface) {
        this.networkInterface = networkInterface;
    }

    public String getNetworkInterface() {
        return networkInterface;
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
        attributeList.add(WSConfigElementUtility.createAttribute(ATTRIBUTE_IP_ADDRESS, this.getIpAddress()
                .getHostAddress()));
        attributeList.add(WSConfigElementUtility.createAttribute(ATTRIBUTE_REALM_ID, this.getRealmId()));
        attributeList.add(WSConfigElementUtility.createAttribute(ATTRIBUTE_START_PORT,
                Integer.toString(this.getStartPort())));
        attributeList.add(WSConfigElementUtility.createAttribute(ATTRIBUTE_END_PORT,
                Integer.toString(this.getEndPort())));

        result.setAttributeList(attributeList);

        logger.debug("wsConfig=" + result);
        WSConfigElement child = new WSConfigElement();
        attributeList = new ArrayList<WSConfigAttribute>();
        // String name = this.getNetworkInterface().substring(0, this.getNetworkInterface().indexOf(":"));
        String subPortId = this.getNetworkInterface().substring(this.getNetworkInterface().indexOf(":"),
                this.getNetworkInterface().length());

        // FIXME name should not be ""
        attributeList.add(WSConfigElementUtility.createAttribute(ATTRIBUTE_NAME, "name"));
        attributeList.add(WSConfigElementUtility.createAttribute(ATTRIBUTE_SUB_PORT_ID, subPortId));
        ArrayList<WSConfigElement> children = new ArrayList<WSConfigElement>();
        children.add(child);
        result.setChildren(children);

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
    public static SteeringPool fromWSConfigElement(WSConfigElement fullElement) {
        SteeringPool result = null;
        String realmId = WSConfigElementUtility.getAttributeByName(fullElement, ATTRIBUTE_REALM_ID);
        String ipAddress = WSConfigElementUtility.getAttributeByName(fullElement, ATTRIBUTE_IP_ADDRESS);
        String startPortString = WSConfigElementUtility.getAttributeByName(fullElement, ATTRIBUTE_START_PORT);
        String endPortString = WSConfigElementUtility.getAttributeByName(fullElement, ATTRIBUTE_END_PORT);
        InetAddress ip = null;
        ip = WSConfigElementUtility.parseAddress(ipAddress);
        int startPort;
        int endPort;

        startPort = Integer.parseInt(startPortString);
        endPort = Integer.parseInt(endPortString);
        result = SteeringPool.create(ip, realmId, startPort, endPort);
        if (null == fullElement.getChildren()) {
            logger.debug("Element has no children\n" + fullElement.toString());
        } else {
            if (!fullElement.getChildren().isEmpty()) {
                WSConfigElement element = fullElement.getChildren().get(0);
                String name = WSConfigElementUtility.getAttributeByName(element, ATTRIBUTE_NAME);
                String subPortId = WSConfigElementUtility.getAttributeByName(element, ATTRIBUTE_SUB_PORT_ID);
                result.setNetworkInterface(name + ":" + subPortId);
            }
        }

        return result;
    }
}
