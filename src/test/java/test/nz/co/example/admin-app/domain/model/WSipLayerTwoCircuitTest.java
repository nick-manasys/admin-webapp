package test.nz.co.example.dev.domain.model;

import static org.fest.assertions.Assertions.assertThat;

import java.net.InetAddress;
import java.net.UnknownHostException;

import junit.framework.Assert;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import nz.co.example.dev.domain.model.AccessRealm;
import nz.co.example.dev.domain.model.NetworkInterface;
import nz.co.example.dev.domain.model.SipInterface;
import nz.co.example.dev.domain.model.SteeringPool;
import nz.co.example.dev.domain.model.WSipLayerTwoCircuit;

import org.junit.BeforeClass;
import org.junit.Test;

import test.nz.co.example.dev.testsupport.Fixtures;

import org.springframework.context.support.StaticApplicationContext;
import static test.nz.co.example.dev.testsupport.Fixtures.*;

public class WSipLayerTwoCircuitTest {

    private static WSipLayerTwoCircuit circuit;

    @BeforeClass
    public static void setup() throws UnknownHostException {
        NetworkInterface networkInterface = Fixtures.createNetworkInterface();
        AccessRealm primaryAccessRealm = Fixtures.createPrimaryAccessRealm();
        AccessRealm secondaryAccessRealm = Fixtures.createSecondaryAccessRealm();
        SipInterface primarySipInterface = Fixtures.createPrimarySipInterface();
        SipInterface secondarySipInterface = Fixtures.createSecondarySipInterface();
        SteeringPool steeringPool = Fixtures.createSteeringPool();
        circuit = new WSipLayerTwoCircuit(networkInterface, primaryAccessRealm, secondaryAccessRealm,
                primarySipInterface, secondarySipInterface, steeringPool, "auc");
    }

    @Test
    public void shouldHaveCorrectCarrierId() {
        assertThat(circuit.getCarrierId()).isEqualTo("tux");
    }

    @Test
    public void shouldHaveCorrectIpAddress() {
        assertThat(circuit.getIpAddress()).isEqualTo(Fixtures.IP_ADDRESS);
    }

    @Test
    public void shouldHaveCorrectRegion() {
        assertThat(circuit.getRegion()).isEqualTo("auc");
    }

    @Test
    public void shouldHaveCorrectVLAN() {
        assertThat(circuit.getVLAN()).isEqualTo("889");
    }

    @Test
    public void shouldVerifyEqualsAndHashCode() throws UnknownHostException {
        EqualsVerifier
                .forClass(WSipLayerTwoCircuit.class)
                .suppress(Warning.NULL_FIELDS, Warning.NONFINAL_FIELDS)
                .withPrefabValues(InetAddress.class, InetAddress.getByName("1.1.1.1"), InetAddress.getByName("1.1.1.2"))
                .verify();

    }

    @Test
    public void shouldIncludePrimaryAccessRealmInToString() {
        String expecting = String
                .format("media-manager%nrealm-config%nidentifier %s%ndescription \"%s\"%naddr-prefix 0.0.0.0%nnetwork-interfaces %s%nmm-in-realm enabled%nmm-in-network enabled%nmm-same-ip disabled%nmm-in-system enabled%nbw-cac-non-mm disabled%nmsm-release disabled%naccess-control-trust-level medium%ninvalid-signal-threshold 1%naverage-rate-limit 0%nmaximum-signal-threshold 4000%nuntrusted-signal-threshold 0%noptions +sip-connect-pbx-reg=rewrite-new%nparent-realm %s",
                        PRIMARY_REALM_ID, PRIMARY_REALM_DESCRIPTION, REALM_NETWORK_INTERFACES,
                        PRIMARY_REALM_PARENT_REALM);
        assertThat(circuit.toString()).contains(expecting);

    }

    @Test
    public void testGetKey() {
        String region = "auc";
        String carrierId = "TEST";
        String trunkNumber = "01";
        String vLAN = "2251";

        String key = region + "-" + vLAN;

        InetAddress secondaryUtilityAddress = null;
        InetAddress ipAddress = null;

        NetworkInterface networkInterface = NetworkInterface.createWithoutGateway("m01", 2251, "description", null,
                null, secondaryUtilityAddress, null);
        AccessRealm primaryAccessRealm = AccessRealm.createPrimary("TEST", "description", "networkInterfaces",
                "parentRealm", region);
        InetAddress tcpSipPortAddress = null;
        InetAddress udpSipPortAddress = null;
        SipInterface primarySipInterface = SipInterface.createPrimary("TEST", "description", udpSipPortAddress,
                tcpSipPortAddress);
        SipInterface secondarySipInterface = SipInterface.createPrimary("TEST", "description", udpSipPortAddress,
                tcpSipPortAddress);
        AccessRealm secondaryAccessRealm = null;
        SteeringPool steeringPool = SteeringPool.create(ipAddress, "TEST", 3000, 3999);

        WSipLayerTwoCircuit circuit = new WSipLayerTwoCircuit(networkInterface, primaryAccessRealm,
                secondaryAccessRealm, primarySipInterface, secondarySipInterface, steeringPool, region);

        Assert.assertTrue(key.equals(circuit.getKey()));
    }
}
