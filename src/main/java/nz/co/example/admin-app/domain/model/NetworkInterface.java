package nz.co.example.dev.domain.model;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Objects;

import nz.co.example.dev.integration.calls.WSConfigElementUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.acmepacket.ems.ws.service.userobjects.WSConfigAttribute;
import com.acmepacket.ems.ws.service.userobjects.WSConfigElement;

/**
 * The Network Interface.
 * 
 * This encapsulates the configured values for a network interface
 * 
 * <p>
 * <b>ACP</b>:<br/>
 * networkInterface<br/>
 * <b>ACLI</b>:<br/>
 * system->network-interface sub-element within the dev.<br/>
 * </p>
 * <br/>
 * A NetworkInterface exists on a PhysicalInterface
 * 
 * @author nivanov
 */
public class NetworkInterface implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 7389181929882673623L;

    public static final String TYPE = "networkInterface";

    private static final Logger logger = LoggerFactory.getLogger(NetworkInterface.class);

    // private static final String IP_ADDRESS =
    // "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";

    // @Size(min = 1)
    private String name;

    /**
     * Must be in the range [0..4095]
     */
    // @NotNull
    // @NumberFormat(style = Style.NUMBER)
    // @Min(0)
    // @Max(4095)
    private int subport;

    private String description;

    // @Pattern(regexp = IP_ADDRESS)
    private InetAddress ipAddress;

    // @Pattern(regexp = IP_ADDRESS)
    private InetAddress primaryUtilityAddress;

    // @Pattern(regexp = IP_ADDRESS)
    private InetAddress secondaryUtilityAddress;

    // @Pattern(regexp = IP_ADDRESS)
    private InetAddress netMask;

    // @Pattern(regexp = IP_ADDRESS)
    private InetAddress gateway;

    private GWHeartbeat heartbeat;

    public static NetworkInterface createWithoutGateway(String name, int subport, String description,
            InetAddress ipAddress, InetAddress primaryUtilityAddress, InetAddress secondaryUtilityAddress,
            InetAddress netMask) {
        return new NetworkInterface(name, subport, description, ipAddress, primaryUtilityAddress,
                secondaryUtilityAddress, netMask, null);
    }

    public static NetworkInterface createWithGateway(String name, int subport, String description,
            InetAddress ipAddress, InetAddress primaryUtilityAddress, InetAddress secondaryUtilityAddress,
            InetAddress netMask, InetAddress gateway) {
        return new NetworkInterface(name, subport, description, ipAddress, primaryUtilityAddress,
                secondaryUtilityAddress, netMask, gateway);
    }

    private NetworkInterface(String name, int subport, String description, InetAddress ipAddress,
            InetAddress primaryUtilityAddress, InetAddress secondaryUtilityAddress, InetAddress netMask,
            InetAddress gateway) {
        super();
        this.name = name;
        this.subport = subport;
        this.description = description;
        this.ipAddress = ipAddress;
        this.primaryUtilityAddress = primaryUtilityAddress;
        this.secondaryUtilityAddress = secondaryUtilityAddress;
        this.netMask = netMask;
        this.gateway = gateway;
        this.heartbeat = GWHeartbeat.createDisabled();
    }

    @Override
    public String toString() {
        if (gateway != null) {
            return String
                    .format("system%nnetwork-interface%nname %s%nsub-port-id %s%ndescription \"%s\"%nip-address %s%npri-utility-addr %s%nsec-utility-addr %s%nnetmask %s%ngateway %s%n%s",
                            name, subport, description, ipAddress.getHostAddress(),
                            primaryUtilityAddress.getHostAddress(), secondaryUtilityAddress.getHostAddress(),
                            netMask.getHostAddress(), gateway.getHostAddress(), heartbeat);
        } else {
            return String
                    .format("system%nnetwork-interface%nname %s%nsub-port-id %s%ndescription \"%s\"%nip-address %s%npri-utility-addr %s%nsec-utility-addr %s%nnetmask %s%n%s",
                            name, subport, description, ipAddress.getHostAddress(),
                            primaryUtilityAddress.getHostAddress(), secondaryUtilityAddress.getHostAddress(),
                            netMask.getHostAddress(), heartbeat);
        }
    }

    @Override
    public final int hashCode() {
        return Objects.hash(description, gateway, heartbeat, ipAddress, name, netMask, primaryUtilityAddress,
                secondaryUtilityAddress, subport);
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof NetworkInterface)) {
            return false;
        }
        NetworkInterface other = (NetworkInterface) obj;
        return Objects.equals(description, other.getDescription()) && Objects.equals(gateway, other.getGateway())
                && Objects.equals(heartbeat, other.getHeartbeat()) && Objects.equals(ipAddress, other.getIpAddress())
                && Objects.equals(name, other.getName()) && Objects.equals(netMask, other.getNetMask())
                && Objects.equals(primaryUtilityAddress, other.getPrimaryUtilityAddress())
                && Objects.equals(secondaryUtilityAddress, other.getSecondaryUtilityAddress())
                && Objects.equals(subport, other.getSubport());
    }

    public String getName() {
        return name;
    }

    public int getSubport() {
        return subport;
    }

    public String getId() {
        return name + ":" + subport;
    }

    public String getDescription() {
        return description;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public InetAddress getPrimaryUtilityAddress() {
        return primaryUtilityAddress;
    }

    public InetAddress getSecondaryUtilityAddress() {
        return secondaryUtilityAddress;
    }

    public InetAddress getNetMask() {
        return netMask;
    }

    public InetAddress getGateway() {
        return gateway;
    }

    public GWHeartbeat getHeartbeat() {
        return heartbeat;
    }

    /**
     * @return
     */
    public WSConfigElement toWSConfigElement() {
        WSConfigElement result = new WSConfigElement();
        ArrayList<WSConfigElement> subElements = new ArrayList<WSConfigElement>();

        // result = configMgmtIF.newConfigElement("dev-LAB", type);
        result.setType(TYPE);
        result.setElementTypePath(TYPE);
        // FIXME this should be generated from data not read from device
        ArrayList<WSConfigAttribute> attributeList = new ArrayList<WSConfigAttribute>();
        attributeList.add(WSConfigElementUtility.createAttribute("name", this.getName()));
        attributeList.add(WSConfigElementUtility.createAttribute("subPortId", Integer.toString(this.getSubport())));
        if (null == this.getGateway()) {
            // FIXME should it be ""
            attributeList.add(WSConfigElementUtility.createAttribute("gateway", "192.168.1.1"));
        } else {
            attributeList.add(WSConfigElementUtility.createAttribute("gateway", this.getGateway().getHostAddress()));
        }
        attributeList.add(WSConfigElementUtility.createAttribute("utilityAddress", this.getPrimaryUtilityAddress()
                .getHostAddress()));
        attributeList.add(WSConfigElementUtility.createAttribute("netmask", this.getNetMask().getHostAddress()));
        attributeList.add(WSConfigElementUtility.createAttribute("secondUtilityAddress", this
                .getSecondaryUtilityAddress().getHostAddress()));

        attributeList.add(WSConfigElementUtility.createAttribute("description", this.getDescription()));
        attributeList.add(WSConfigElementUtility.createAttribute("ipAddress", this.getIpAddress().getHostAddress()));

        // fields that need to be there
        attributeList.add(WSConfigElementUtility.createAttribute("gatewaySec", ""));
        attributeList.add(WSConfigElementUtility.createAttribute("sshAddress", ""));

        attributeList.add(WSConfigElementUtility.createAttribute("hostname", ""));

        attributeList.add(WSConfigElementUtility.createAttribute("domNameServerB1", ""));
        attributeList.add(WSConfigElementUtility.createAttribute("domNameServerB2", ""));
        attributeList.add(WSConfigElementUtility.createAttribute("ftpAddress", ""));
        attributeList.add(WSConfigElementUtility.createAttribute("snmpAddress", ""));
        attributeList.add(WSConfigElementUtility.createAttribute("dataVersion", ""));
        attributeList.add(WSConfigElementUtility.createAttribute("telnetAddress", ""));
        attributeList.add(WSConfigElementUtility.createAttribute("domNameServer", ""));
        // FIXME this is not in our data structure
        attributeList.add(WSConfigElementUtility.createAttribute("defDomainName", "softswitch.example.net"));
        attributeList.add(WSConfigElementUtility.createAttribute("dnsTimeout", "11"));
        // Name : lastModifiedBy Value : admin@10.200.33.85,
        // Name : lastModifiedDate Value : 2012-03-19 08:11:49,
        result.setAttributeList(attributeList);

        // FIXME TODO NB addresses are hard coded here, NB the pairs of hip and icmp ip numbers have to be unique
        logger.debug("wsConfig=" + result);
        WSConfigElement child;
        // networkInterface/hip-ip-list (a: ip)
        child = new WSConfigElement();
        child.setElementTypePath("networkInterface/hip-ip-list");
        child.setType("networkInterface/hip-ip-list");
        child.setAttributeList(new ArrayList<WSConfigAttribute>());
        child.getAttributeList().add(WSConfigElementUtility.createAttribute("ip", "192.168.1.2"));
        subElements.add(child);
        // networkInterface/hip-ip-list (a: ip)
        child = new WSConfigElement();
        child.setElementTypePath("networkInterface/hip-ip-list");
        child.setType("networkInterface/hip-ip-list");
        child.setAttributeList(new ArrayList<WSConfigAttribute>());
        child.getAttributeList().add(WSConfigElementUtility.createAttribute("ip", "192.168.1.3"));
        subElements.add(child);
        // networkInterface/icmp-ip-list (a: ip)
        child = new WSConfigElement();
        child.setElementTypePath("networkInterface/icmp-ip-list");
        child.setType("networkInterface/icmp-ip-list");
        child.setAttributeList(new ArrayList<WSConfigAttribute>());
        child.getAttributeList().add(WSConfigElementUtility.createAttribute("ip", "192.168.1.4"));
        subElements.add(child);
        // networkInterface/icmp-ip-list (a: ip)
        child = new WSConfigElement();
        child.setElementTypePath("networkInterface/icmp-ip-list");
        child.setType("networkInterface/icmp-ip-list");
        child.setAttributeList(new ArrayList<WSConfigAttribute>());
        child.getAttributeList().add(WSConfigElementUtility.createAttribute("ip", "192.168.1.5"));
        subElements.add(child);
        // networkInterface/GWHeartbeat (a: retryTimeout=1, a: healthDec=0, a: retryCount=0, a: state=enabled, a:
        // timeout=10)
        child = new WSConfigElement();
        child.setElementTypePath("networkInterface/GWHeartbeat");
        child.setType("networkInterface/GWHeartbeat");
        child.setAttributeList(new ArrayList<WSConfigAttribute>());
        child.getAttributeList().add(WSConfigElementUtility.createAttribute("retryTimeout", "1"));
        child.getAttributeList().add(WSConfigElementUtility.createAttribute("healthDec", "0"));
        child.getAttributeList().add(WSConfigElementUtility.createAttribute("retryCount", "0"));
        child.getAttributeList().add(WSConfigElementUtility.createAttribute("state", "enabled"));
        child.getAttributeList().add(WSConfigElementUtility.createAttribute("timeout", "10"));
        subElements.add(child);

        if (null != subElements) {
            result.setChildren(subElements);
        }
        return result;
    }

    /**
     * @param element
     * @return
     */
    public static NetworkInterface fromWSConfigElement(WSConfigElement fullElement) {
        NetworkInterface result = null;
        String name;
        int subport = 0;
        String description;
        InetAddress ipAddress;
        InetAddress primaryUtilityAddress;
        InetAddress secondaryUtilityAddress;
        InetAddress netMask;
        InetAddress gateway;

        name = WSConfigElementUtility.getAttributeByName(fullElement, "name");
        description = WSConfigElementUtility.getAttributeByName(fullElement, "description");
        String subportString = WSConfigElementUtility.getAttributeByName(fullElement, "subPortId");
        String ipAddressString = WSConfigElementUtility.getAttributeByName(fullElement, "ipAddress");
        String primaryUtilityAddressString = WSConfigElementUtility.getAttributeByName(fullElement, "utilityAddress");
        String secondaryUtilityAddressString = WSConfigElementUtility.getAttributeByName(fullElement,
                "secondUtilityAddress");
        String netMaskString = WSConfigElementUtility.getAttributeByName(fullElement, "netmask");
        String gatewayString = WSConfigElementUtility.getAttributeByName(fullElement, "gateway");

        ipAddress = WSConfigElementUtility.parseAddress(ipAddressString);
        primaryUtilityAddress = WSConfigElementUtility.parseAddress(primaryUtilityAddressString);
        secondaryUtilityAddress = WSConfigElementUtility.parseAddress(secondaryUtilityAddressString);
        netMask = WSConfigElementUtility.parseAddress(netMaskString);
        gateway = WSConfigElementUtility.parseAddress(gatewayString);
        if (StringUtils.hasLength(subportString)) {
            try {
                subport = Integer.parseInt(subportString);
            } catch (NumberFormatException e1) {
                // TODO Auto-generated catch block
                // e1.printStackTrace();
            }
        }
        // FIXME verify below test
        if (StringUtils.hasLength(WSConfigElementUtility.getAttributeByName(fullElement, "gateway"))) {
            result = NetworkInterface.createWithGateway(name, subport, description, ipAddress, primaryUtilityAddress,
                    secondaryUtilityAddress, netMask, gateway);
        } else {
            result = NetworkInterface.createWithoutGateway(name, subport, description, ipAddress,
                    primaryUtilityAddress, secondaryUtilityAddress, netMask);
        }
        return result;
    }
}
