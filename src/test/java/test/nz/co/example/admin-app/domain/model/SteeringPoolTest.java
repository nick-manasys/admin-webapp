package test.nz.co.example.dev.domain.model;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import nz.co.example.dev.domain.model.SteeringPool;
import nz.co.example.dev.integration.calls.WSConfigElementUtility;

import org.junit.Test;

import test.nz.co.example.dev.testsupport.Fixtures;

import com.acmepacket.ems.ws.service.userobjects.WSConfigAttribute;
import com.acmepacket.ems.ws.service.userobjects.WSConfigElement;
import static test.nz.co.example.dev.testsupport.Fixtures.*;

public class SteeringPoolTest {

    @Test
    public void shouldCreateCorrectToStringForSteeringPool() throws UnknownHostException {
        // Given
        SteeringPool steeringPool = Fixtures.createSteeringPool();

        // When
        String steeringPoolToString = steeringPool.toString();

        // Then
        String expecting = String.format(
                "media-manager%nsteering-pool%nip-address %s%nstart-port 16384%nend-port 17384%nrealm-id %s%n",
                IP_ADDRESS, PRIMARY_REALM_ID);
        assertThat(steeringPoolToString).isEqualTo(expecting);
    }

    @Test
    public void shouldVerifyEqualsAndHashCode() throws UnknownHostException {
        EqualsVerifier
                .forClass(SteeringPool.class)
                .suppress(Warning.NULL_FIELDS, Warning.NONFINAL_FIELDS)
                .withPrefabValues(InetAddress.class, InetAddress.getByName("1.1.1.1"), InetAddress.getByName("1.1.1.2"))
                .verify();
    }

    @Test
    public void testToWSConfigElement() throws Exception {
        // FIXME check all fields
        SteeringPool steeringPool = null;

        InetAddress ipAddress = WSConfigElementUtility.parseAddress("192.168.1.1");
        String realmId = "accessRealm";
        int startPort = 1000;
        int endPort = 2000;

        steeringPool = SteeringPool.create(ipAddress, realmId, startPort, endPort);

        WSConfigElement fullElement = steeringPool.toWSConfigElement();

        // assertions
        assertEquals(steeringPool.getIpAddress().getHostAddress(),
                WSConfigElementUtility.getAttributeByName(fullElement, "ipAddress"));
        assertEquals(steeringPool.getRealmId(), WSConfigElementUtility.getAttributeByName(fullElement, "realmID"));
        assertEquals(Integer.toString(steeringPool.getStartPort()),
                WSConfigElementUtility.getAttributeByName(fullElement, "startPort"));
        assertEquals(Integer.toString(steeringPool.getEndPort()),
                WSConfigElementUtility.getAttributeByName(fullElement, "endPort"));
    }

    @Test
    public void testFromWSConfigElement() throws Exception {
        // FIXME check all fields
        WSConfigElement fullElement = null;
        fullElement = new WSConfigElement();
        ArrayList<WSConfigAttribute> attributeList = new ArrayList<WSConfigAttribute>();
        attributeList.add(WSConfigElementUtility.createAttribute("ipAddress", "192.168.1.1"));
        attributeList.add(WSConfigElementUtility.createAttribute("realmID", "accessRealm"));
        attributeList.add(WSConfigElementUtility.createAttribute("startPort", "1000"));
        attributeList.add(WSConfigElementUtility.createAttribute("endPort", "2000"));
        fullElement.setAttributeList(attributeList);
        SteeringPool steeringPool;

        steeringPool = SteeringPool.fromWSConfigElement(fullElement);

        // assertions
        assertEquals(steeringPool.getIpAddress().getHostAddress(),
                WSConfigElementUtility.getAttributeByName(fullElement, "ipAddress"));
        assertEquals(steeringPool.getRealmId(), WSConfigElementUtility.getAttributeByName(fullElement, "realmID"));
        assertEquals(Integer.toString(steeringPool.getStartPort()),
                WSConfigElementUtility.getAttributeByName(fullElement, "startPort"));
        assertEquals(Integer.toString(steeringPool.getEndPort()),
                WSConfigElementUtility.getAttributeByName(fullElement, "endPort"));
    }
}
