package test.nz.co.example.dev.domain.model;

import static nl.jqno.equalsverifier.Warning.NONFINAL_FIELDS;
import static nl.jqno.equalsverifier.Warning.NULL_FIELDS;
import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import nl.jqno.equalsverifier.EqualsVerifier;
import nz.co.example.dev.domain.model.NetworkInterface;
import nz.co.example.dev.integration.calls.WSConfigElementUtility;

import org.junit.Test;

import test.nz.co.example.dev.testsupport.Fixtures;

import com.acmepacket.ems.ws.service.userobjects.WSConfigAttribute;
import com.acmepacket.ems.ws.service.userobjects.WSConfigElement;
import static test.nz.co.example.dev.testsupport.Fixtures.*;

public class NetworkInterfaceTest {

    @Test
    public void shouldCreateCorrectToStringForNetworkInterfaceWithGateway() throws UnknownHostException {
        // Given
        NetworkInterface networkInterface = Fixtures.createNetworkInterface();

        // When
        String networkInterfaceToString = networkInterface.toString();

        // Then
        String expecting = String
                .format("system%nnetwork-interface%nname %s%nsub-port-id %s%ndescription \"%s\"%nip-address %s%npri-utility-addr %s%nsec-utility-addr %s%nnetmask %s%ngateway %s%ngw-heartbeat%nstate disabled",
                        NETWORK_INTERFACE_NAME, NETWORK_INTERFACE_SUB_PORT, NETWORK_INTERFACE_DESCRIPTION, IP_ADDRESS,
                        PRIMARY_UTILITY_ADDRESS, SECONDARY_UTILITY_ADDRESS, NET_MASK, GATEWAY);
        assertThat(networkInterfaceToString).isEqualTo(expecting);
    }

    @Test
    public void shouldCreateCorrectToStringForNetworkInterfaceWithoutGateway() throws UnknownHostException {
        // Given
        NetworkInterface networkInterface = Fixtures.createNetworkInterfaceWithoutGateway();

        // When
        String networkInterfaceToString = networkInterface.toString();

        // Then
        String expecting = String
                .format("system%nnetwork-interface%nname %s%nsub-port-id %s%ndescription \"%s\"%nip-address %s%npri-utility-addr %s%nsec-utility-addr %s%nnetmask %s%ngw-heartbeat%nstate disabled",
                        NETWORK_INTERFACE_NAME, NETWORK_INTERFACE_SUB_PORT, NETWORK_INTERFACE_DESCRIPTION, IP_ADDRESS,
                        PRIMARY_UTILITY_ADDRESS, SECONDARY_UTILITY_ADDRESS, NET_MASK);

        assertThat(networkInterfaceToString).isEqualTo(expecting);
    }

    @Test
    public void shouldVerifyEqualsAndHashCode() throws UnknownHostException {
        EqualsVerifier
                .forClass(NetworkInterface.class)
                .suppress(NULL_FIELDS, NONFINAL_FIELDS)
                .withPrefabValues(InetAddress.class, InetAddress.getByName("1.1.1.1"), InetAddress.getByName("1.1.1.2"))
                .verify();
    }

    @Test
    public void testToWSConfigElement() throws Exception {
        // FIXME check all fields
        NetworkInterface networkInterface = null;
        String name = "m01-access";
        int subport = 889;
        String description = "Test network interface";
        InetAddress ipAddress = WSConfigElementUtility.parseAddress("192.168.1.1");
        InetAddress primaryUtilityAddress = WSConfigElementUtility.parseAddress("192.168.1.2");
        InetAddress secondaryUtilityAddress = WSConfigElementUtility.parseAddress("192.168.1.3");
        InetAddress netMask = WSConfigElementUtility.parseAddress("192.168.1.4");
        InetAddress gateway = WSConfigElementUtility.parseAddress("255.255.255.0");
        networkInterface = NetworkInterface.createWithGateway(name, subport, description, ipAddress,
                primaryUtilityAddress, secondaryUtilityAddress, netMask, gateway);
        WSConfigElement fullElement = networkInterface.toWSConfigElement();
        assertNotNull(fullElement);
        assertEquals(WSConfigElementUtility.getAttributeByName(fullElement, "name"), name);
        assertEquals(WSConfigElementUtility.getAttributeByName(fullElement, "subPortId"), Integer.toString(subport));
        assertEquals(WSConfigElementUtility.getAttributeByName(fullElement, "description"),
                networkInterface.getDescription());
        assertEquals(WSConfigElementUtility.getAttributeByName(fullElement, "ipAddress"), networkInterface
                .getIpAddress().getHostAddress());
        assertEquals(WSConfigElementUtility.getAttributeByName(fullElement, "utilityAddress"), networkInterface
                .getPrimaryUtilityAddress().getHostAddress());
        assertEquals(WSConfigElementUtility.getAttributeByName(fullElement, "secondUtilityAddress"), networkInterface
                .getSecondaryUtilityAddress().getHostAddress());
        assertEquals(WSConfigElementUtility.getAttributeByName(fullElement, "netmask"), networkInterface.getNetMask()
                .getHostAddress());
        assertEquals(WSConfigElementUtility.getAttributeByName(fullElement, "gateway"), networkInterface.getGateway()
                .getHostAddress());
    }

    @Test
    public void testFromWSConfigElement() throws Exception {
        // FIXME check all fields
        String name = "m01-access";
        int subport = 889;
        String description = "Test network interface";
        InetAddress ipAddress = WSConfigElementUtility.parseAddress("192.168.1.1");
        InetAddress primaryUtilityAddress = WSConfigElementUtility.parseAddress("192.168.1.2");
        InetAddress secondaryUtilityAddress = WSConfigElementUtility.parseAddress("192.168.1.3");
        InetAddress netMask = WSConfigElementUtility.parseAddress("192.168.1.4");
        InetAddress gateway = WSConfigElementUtility.parseAddress("255.255.255.0");
        WSConfigElement fullElement = new WSConfigElement();
        ArrayList<WSConfigAttribute> attributeList = new ArrayList<WSConfigAttribute>();
        attributeList.add(WSConfigElementUtility.createAttribute("name", name));
        attributeList.add(WSConfigElementUtility.createAttribute("description", description));
        attributeList.add(WSConfigElementUtility.createAttribute("subPortId", Integer.toString(subport)));
        attributeList.add(WSConfigElementUtility.createAttribute("ipAddress", ipAddress.getHostAddress()));
        attributeList.add(WSConfigElementUtility.createAttribute("utilityAddress", ipAddress.getHostAddress()));
        attributeList.add(WSConfigElementUtility.createAttribute("secondUtilityAddress", ipAddress.getHostAddress()));
        attributeList.add(WSConfigElementUtility.createAttribute("netmask", ipAddress.getHostAddress()));
        attributeList.add(WSConfigElementUtility.createAttribute("gateway", ipAddress.getHostAddress()));
        fullElement.setAttributeList(attributeList);
        NetworkInterface networkInterface;

        networkInterface = NetworkInterface.fromWSConfigElement(fullElement);

        assertNotNull(networkInterface);
        assertEquals(networkInterface.getDescription(),
                WSConfigElementUtility.getAttributeByName(fullElement, "description"));
        assertNotNull(fullElement);
        assertEquals(WSConfigElementUtility.getAttributeByName(fullElement, "name"), name);
        assertEquals(WSConfigElementUtility.getAttributeByName(fullElement, "subPortId"), Integer.toString(subport));
        assertEquals(WSConfigElementUtility.getAttributeByName(fullElement, "description"),
                networkInterface.getDescription());
        assertEquals(WSConfigElementUtility.getAttributeByName(fullElement, "ipAddress"), networkInterface
                .getIpAddress().getHostAddress());
        assertEquals(WSConfigElementUtility.getAttributeByName(fullElement, "utilityAddress"), networkInterface
                .getPrimaryUtilityAddress().getHostAddress());
        assertEquals(WSConfigElementUtility.getAttributeByName(fullElement, "secondUtilityAddress"), networkInterface
                .getSecondaryUtilityAddress().getHostAddress());
        assertEquals(WSConfigElementUtility.getAttributeByName(fullElement, "netmask"), networkInterface.getNetMask()
                .getHostAddress());
        assertEquals(WSConfigElementUtility.getAttributeByName(fullElement, "gateway"), networkInterface.getGateway()
                .getHostAddress());
    }
}
