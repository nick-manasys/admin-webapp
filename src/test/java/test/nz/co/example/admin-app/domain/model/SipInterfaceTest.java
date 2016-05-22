package test.nz.co.example.dev.domain.model;

import static nl.jqno.equalsverifier.Warning.NONFINAL_FIELDS;
import static nl.jqno.equalsverifier.Warning.NULL_FIELDS;
import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static test.nz.co.example.dev.testsupport.Fixtures.IP_ADDRESS;
import static test.nz.co.example.dev.testsupport.Fixtures.PRIMARY_REALM_ID;
import static test.nz.co.example.dev.testsupport.Fixtures.PRIMARY_SIP_DESCRIPTION;
import static test.nz.co.example.dev.testsupport.Fixtures.SECONDARY_SIP_DESCRIPTION;
import static test.nz.co.example.dev.testsupport.Fixtures.SIP_TCP_IP_ADDRESS;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import nl.jqno.equalsverifier.EqualsVerifier;
import nz.co.example.dev.domain.model.SipInterface;
import nz.co.example.dev.integration.calls.WSConfigElementUtility;

import org.junit.Test;

import test.nz.co.example.dev.testsupport.Fixtures;

import com.acmepacket.ems.ws.service.userobjects.WSConfigAttribute;
import com.acmepacket.ems.ws.service.userobjects.WSConfigElement;

public class SipInterfaceTest {

    @Test
    public void shouldCreateCorrectToStringForPrimarySipInterface() throws UnknownHostException {
        // Given
        SipInterface sipInterface = Fixtures.createPrimarySipInterface();

        // When
        String sipInterfaceToString = sipInterface.toString();

        // Then
        String expecting = String
                .format("session-router%nsip-interface%nstate enabled%nrealm-id %s%ndescription \"%s\"%nsip-port%naddress %s%nport 5060%ntransport-protocol UDP%nallow-anonymous registered%n%nsip-port%naddress %s%nport 5060%ntransport-protocol TCP%nallow-anonymous registered%nredirect-action proxy%ncontact-mode none%nnat-traversal none%nregistration-caching enabled%nmin-reg-expire 3600%nregistration-interval 3600%nroute-to-registrar enabled%noptions +reg-via-key%noptions +reg-no-port-match%nsip-ims-feature enabled%ntrust-mode registered%nstop-recurse 401,407%n",
                        PRIMARY_REALM_ID, PRIMARY_SIP_DESCRIPTION, IP_ADDRESS, SIP_TCP_IP_ADDRESS);
        assertThat(sipInterfaceToString).isEqualTo(expecting);
    }

    @Test
    public void shouldCreateCorrectToStringForSecondarySipInterface() throws UnknownHostException {
        // Given
        SipInterface sipInterface = Fixtures.createSecondarySipInterface();

        // When
        String sipInterfaceToString = sipInterface.toString();

        // Then
        String expecting = String
                .format("session-router%nsip-interface%nstate enabled%nrealm-id %s%ndescription \"%s\"%nsip-port%naddress %s%nport 5080%ntransport-protocol UDP%nallow-anonymous registered%n%nredirect-action proxy%ncontact-mode none%nnat-traversal always%nregistration-caching enabled%nnat-interval 30%nmin-reg-expire 3600%nregistration-interval 3600%nroute-to-registrar enabled%noptions +reg-via-key%noptions +reg-no-port-match%nsip-ims-feature enabled%ntrust-mode registered%nstop-recurse 401,407%n",
                        PRIMARY_REALM_ID, SECONDARY_SIP_DESCRIPTION, IP_ADDRESS);

        assertThat(sipInterfaceToString).isEqualTo(expecting);
    }

    @Test
    public void shouldVerifyEqualsAndHashCode() throws UnknownHostException {
        EqualsVerifier
                .forClass(SipInterface.class)
                .suppress(NULL_FIELDS, NONFINAL_FIELDS)
                .withPrefabValues(InetAddress.class, InetAddress.getByName("1.1.1.1"), InetAddress.getByName("1.1.1.2"))
                .verify();
    }

    @Test
    public void testToWSConfigElement() throws Exception {
        // FIXME check all fields
        SipInterface sipInterface;
        WSConfigElement fullElement;

        String realmId;
        String description;
        InetAddress udpSipPortAddress;
        InetAddress tcpSipPortAddress;

        realmId = "accessRealm";
        description = "test ip interface description";
        udpSipPortAddress = WSConfigElementUtility.parseAddress("192.168.1.1");
        tcpSipPortAddress = WSConfigElementUtility.parseAddress("192.168.1.2");

        sipInterface = SipInterface.createPrimary(realmId, description, udpSipPortAddress, tcpSipPortAddress);

        fullElement = sipInterface.toWSConfigElement();

        // assertions
        assertNotNull(sipInterface);
        // check parts match
        // assertEquals(sipInterface.getId(), WSConfigElementUtility.getAttributeByName(fullElement, ""));
        assertEquals(sipInterface.getRealmId(), WSConfigElementUtility.getAttributeByName(fullElement, "RealmID"));
        assertEquals(sipInterface.getDescription(),
                WSConfigElementUtility.getAttributeByName(fullElement, "description"));
        // UDP
        assertEquals(sipInterface.getUdpSipPort().getAddress().getHostAddress(),
                WSConfigElementUtility.getChildAttributeByNameWhichHasAttributeWithValue(fullElement, "address",
                        "transProtocol", "UDP"));
        assertEquals(sipInterface.getUdpSipPort().getTransportProtocol().name(),
                WSConfigElementUtility.getChildAttributeByNameWhichHasAttributeWithValue(fullElement, "transProtocol",
                        "transProtocol", "UDP"));
        assertEquals(Integer.toString(sipInterface.getUdpSipPort().getPort()),
                WSConfigElementUtility.getChildAttributeByNameWhichHasAttributeWithValue(fullElement, "port",
                        "transProtocol", "UDP"));
        // TCP
        assertEquals(sipInterface.getTcpSipPort().getAddress().getHostAddress(),
                WSConfigElementUtility.getChildAttributeByNameWhichHasAttributeWithValue(fullElement, "address",
                        "transProtocol", "TCP"));
        assertEquals(sipInterface.getTcpSipPort().getTransportProtocol().name(),
                WSConfigElementUtility.getChildAttributeByNameWhichHasAttributeWithValue(fullElement, "transProtocol",
                        "transProtocol", "TCP"));
        assertEquals(Integer.toString(sipInterface.getTcpSipPort().getPort()),
                WSConfigElementUtility.getChildAttributeByNameWhichHasAttributeWithValue(fullElement, "port",
                        "transProtocol", "TCP"));
    }

    @Test
    public void testFromWSConfigElement() throws Exception {
        // FIXME check all fields
        WSConfigElement fullElement;
        SipInterface sipInterface;

        fullElement = new WSConfigElement();
        ArrayList<WSConfigAttribute> attributeList = new ArrayList<WSConfigAttribute>();
        fullElement.setAttributeList(attributeList);
        attributeList.add(WSConfigElementUtility.createAttribute("port", "1000"));

        ArrayList<WSConfigElement> children = new ArrayList<WSConfigElement>();
        fullElement.setChildren(children);

        WSConfigElement child;
        // udp sip port
        child = new WSConfigElement();
        child.setElementTypePath(SipInterface.TYPE + "/sipPort");
        child.setType(SipInterface.TYPE + "/sipPort");
        child.setAttributeList(new ArrayList<WSConfigAttribute>());
        child.getAttributeList().add(WSConfigElementUtility.createAttribute("tlsProfile", ""));
        child.getAttributeList().add(WSConfigElementUtility.createAttribute("transProtocol", "UDP"));
        child.getAttributeList().add(WSConfigElementUtility.createAttribute("address", "192.168.1.1"));
        // child.getAttributeList().add(
        // WSConfigElementUtility
        // .createAttribute("anonMode", this.getUdpSipPort().getAnonMode().getDisplayValue()));
        child.getAttributeList().add(WSConfigElementUtility.createAttribute("imsAkaProfile", ""));
        children.add(child);

        // tcp sip port
        child = new WSConfigElement();
        child.setElementTypePath(SipInterface.TYPE + "/sipPort");
        child.setType(SipInterface.TYPE + "/sipPort");
        child.setAttributeList(new ArrayList<WSConfigAttribute>());
        child.getAttributeList().add(WSConfigElementUtility.createAttribute("tlsProfile", ""));
        child.getAttributeList().add(WSConfigElementUtility.createAttribute("transProtocol", "TCP"));
        child.getAttributeList().add(WSConfigElementUtility.createAttribute("address", "192.168.1.2"));
        // child.getAttributeList().add(
        // WSConfigElementUtility
        // .createAttribute("anonMode", this.getUdpSipPort().getAnonMode().getDisplayValue()));
        child.getAttributeList().add(WSConfigElementUtility.createAttribute("imsAkaProfile", ""));
        children.add(child);

        sipInterface = SipInterface.fromWSConfigElement(fullElement);

        // assertions
        assertNotNull(sipInterface);
        // check parts match
        // assertEquals(sipInterface.getId(), WSConfigElementUtility.getAttributeByName(fullElement, ""));
        assertEquals(sipInterface.getRealmId(), WSConfigElementUtility.getAttributeByName(fullElement, "RealmID"));
        assertEquals(sipInterface.getDescription(),
                WSConfigElementUtility.getAttributeByName(fullElement, "description"));
        // UDP
        assertEquals(sipInterface.getUdpSipPort().getAddress().getHostAddress(),
                WSConfigElementUtility.getChildAttributeByNameWhichHasAttributeWithValue(fullElement, "address",
                        "transProtocol", "UDP"));
        assertEquals(sipInterface.getUdpSipPort().getTransportProtocol().name(),
                WSConfigElementUtility.getChildAttributeByNameWhichHasAttributeWithValue(fullElement, "transProtocol",
                        "transProtocol", "UDP"));
        // assertEquals(Integer.toString(sipInterface.getUdpSipPort().getPort()),
        // WSConfigElementUtility.getChildAttributeByNameWhichHasAttributeWithValue(fullElement, "port",
        // "transProtocol", "UDP"));
        // TCP
        assertEquals(sipInterface.getTcpSipPort().getAddress().getHostAddress(),
                WSConfigElementUtility.getChildAttributeByNameWhichHasAttributeWithValue(fullElement, "address",
                        "transProtocol", "TCP"));
        assertEquals(sipInterface.getTcpSipPort().getTransportProtocol().name(),
                WSConfigElementUtility.getChildAttributeByNameWhichHasAttributeWithValue(fullElement, "transProtocol",
                        "transProtocol", "TCP"));
        // assertEquals(Integer.toString(sipInterface.getTcpSipPort().getPort()),
        // WSConfigElementUtility.getChildAttributeByNameWhichHasAttributeWithValue(fullElement, "port",
        // "transProtocol", "TCP"));
    }
}
