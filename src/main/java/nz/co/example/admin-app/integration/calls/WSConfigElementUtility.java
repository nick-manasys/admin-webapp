package nz.co.example.dev.integration.calls;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import nz.co.example.dev.domain.model.NetworkInterface;
import nz.co.example.dev.domain.model.SipInterface;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.acmepacket.ems.ws.service.userobjects.WSConfigAttribute;
import com.acmepacket.ems.ws.service.userobjects.WSConfigElement;

/**
 * @author Nick
 * 
 */
public class WSConfigElementUtility {
    private static final Logger logger = LoggerFactory.getLogger(WSConfigElementUtility.class);

    public static String getSipDefinitionTwoHostAddress(SipInterface primarySipInterface) {
        // FIXME need a default
        String result = "218.101.10.136";
        if (null != primarySipInterface && null != primarySipInterface.getTcpSipPort()
                && null != primarySipInterface.getTcpSipPort().getAddress()
                && null != primarySipInterface.getTcpSipPort().getAddress().getHostAddress()) {
            result = primarySipInterface.getTcpSipPort().getAddress().getHostAddress();
        }
        return result;
    }

    public static InetAddress parseAddress(String inputAddress) {
        InetAddress result = null;
        if (StringUtils.hasLength(inputAddress)) {
            if (inputAddress.contains("/")) {
                inputAddress = inputAddress.substring(0, inputAddress.indexOf("/"));
            }
            try {
                result = InetAddress.getByName(inputAddress);
            } catch (UnknownHostException e) {
                throw new RuntimeException("Could not parse address " + inputAddress, e);
            }
        }
        return result;
    }

    public static String getAttributeByName(WSConfigElement e, String attributeName) {
        return findAttribute(e, attributeName);
    }

    private static String findAttribute(WSConfigElement e, String attributeName) {
        String result = "";
        if (null != e.getAttributeList()) {
            for (WSConfigAttribute attribute : e.getAttributeList()) {
                if (attributeName.equals(attribute.getName())) {
                    result = attribute.getValue();
                }
            }
        }
        return result;
    }

    private static boolean hasAttributeWithValue(WSConfigElement e, String attributeName, String attributeValue) {
        boolean result = false;
        if (null != e.getAttributeList()) {
            for (WSConfigAttribute attribute : e.getAttributeList()) {
                if (attributeName.equals(attribute.getName())) {
                    result = attributeValue.equals(attribute.getValue());
                }
            }
        }
        return result;
    }

    public static String getChildAttributeByNameWhichHasAttributeWithValue(WSConfigElement e, String attributeName,
            String matchedAttributeName, String matchedAttributeValue) {
        String result = null;
        for (WSConfigElement child : e.getChildren()) {
            if (hasAttributeWithValue(child, matchedAttributeName, matchedAttributeValue)) {
                result = findAttribute(child, attributeName);
            }
        }
        return result;
    }

    /**
     * Get a collection of children by type.
     * 
     * @param fullConfigElement
     * @param string
     * @return
     */
    public static List<NetworkInterface> getChildrenByType(WSConfigElement fullElement, String type) {
        List<NetworkInterface> result = new ArrayList<NetworkInterface>();
        NetworkInterface networkInterface;
        if (null != fullElement.getChildren()) {
            for (WSConfigElement child : fullElement.getChildren()) {
                if (hasType(child, type)) {
                    String name = WSConfigElementUtility.getAttributeByName(child, "name");
                    String description = ""; // TODO WSConfigElementUtility.getAttributeByName(fullConfigElement,
                                             // "description");
                    String ipAddress = ""; // TODO WSConfigElementUtility.getAttributeByName(fullConfigElement,
                                           // "ipAddress");
                    String primaryUtilityAddress = "";
                    String secondaryUtilityAddress = "";
                    String netMaskString = "";
                    String gatewayString = "";
                    String subportString = WSConfigElementUtility.getAttributeByName(child, "subPortId");
                    InetAddress ip = null;
                    int subport = 0;
                    InetAddress primaryUtility = null;
                    InetAddress secondaryUtility = null;
                    InetAddress netMask = null;
                    InetAddress gateway = null;
                    String state = "";
                    if (StringUtils.hasLength(ipAddress)) {
                        try {
                            ip = InetAddress.getByName(ipAddress);
                        } catch (UnknownHostException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                    if (StringUtils.hasLength(primaryUtilityAddress)) {
                        try {
                            primaryUtility = InetAddress.getByName(primaryUtilityAddress);
                        } catch (UnknownHostException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                    if (StringUtils.hasLength(secondaryUtilityAddress)) {
                        try {
                            secondaryUtility = InetAddress.getByName(secondaryUtilityAddress);
                        } catch (UnknownHostException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                    if (StringUtils.hasLength(netMaskString)) {
                        try {
                            netMask = InetAddress.getByName(netMaskString);
                        } catch (UnknownHostException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                    if (StringUtils.hasLength(gatewayString)) {
                        try {
                            gateway = InetAddress.getByName(gatewayString);
                        } catch (UnknownHostException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                    try {
                        subport = Integer.parseInt(subportString);
                    } catch (NumberFormatException e1) {
                        // TODO Auto-generated catch block
                        // e1.printStackTrace();
                    }
                    if (true) {
                        networkInterface = NetworkInterface.createWithGateway(name, subport, description, ip,
                                primaryUtility, secondaryUtility, netMask, gateway);
                    } else {
                        networkInterface = NetworkInterface.createWithoutGateway(name, subport, description, ip,
                                primaryUtility, secondaryUtility, netMask);
                    }
                    result.add(networkInterface);
                }
            }
        }

        return result;
    }

    private static boolean hasType(WSConfigElement e, String type) {
        return type.equals(e.getType());
    }

    public static WSConfigAttribute createAttribute(String name, String value) {
        WSConfigAttribute result = new WSConfigAttribute();
        result.setName(name);
        result.setValue(value);
        return result;
    }

    public static void printElement(WSConfigElement element) {
        if (false) {
            logger.debug("-------------");
            logger.debug(element.toString());
            logger.debug("-------------");
            printElement(0, element);
        }
    }

    public static void printElement(int level, WSConfigElement element) {
        logger.debug(indent(level) + "elementTypePath: " + element.getElementTypePath());
        logger.debug(indent(level) + "type: " + element.getType());
        printAttributeList(level + 1, element.getAttributeList());
        printChildren(level + 1, element.getChildren());
    }

    private static void printChildren(int level, ArrayList<WSConfigElement> children) {
        if (null != children) {
            for (WSConfigElement element : children) {
                printElement(level + 1, element);
            }
        }
    }

    private static void printAttributeList(int level, ArrayList<WSConfigAttribute> attributeList) {
        if (null != attributeList) {
            for (WSConfigAttribute attribute : attributeList) {
                printAttribute(level, attribute);
            }
        }
    }

    private static void printAttribute(int level, WSConfigAttribute attribute) {
        logger.debug(indent(level) + attribute.getName() + "=" + attribute.getValue());
    }

    private static String indent(int level) {
        String result = "";
        for (int i = 0; i <= level; i++) {
            result += "+---";
        }
        return result;
    }
}
