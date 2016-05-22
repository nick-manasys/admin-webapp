package test.nz.co.example.dev.domain.logic;

import static org.fest.assertions.Assertions.assertThat;
import static test.nz.co.example.dev.testsupport.Fixtures.ALWAYS;
import static test.nz.co.example.dev.testsupport.Fixtures.DISABLED;
import static test.nz.co.example.dev.testsupport.Fixtures.ENABLED;
import static test.nz.co.example.dev.testsupport.Fixtures.GATEWAY;
import static test.nz.co.example.dev.testsupport.Fixtures.IP_ADDRESS;
import static test.nz.co.example.dev.testsupport.Fixtures.MEDIUM;
import static test.nz.co.example.dev.testsupport.Fixtures.NETWORK_INTERFACE_DESCRIPTION;
import static test.nz.co.example.dev.testsupport.Fixtures.NETWORK_INTERFACE_NAME;
import static test.nz.co.example.dev.testsupport.Fixtures.NET_MASK;
import static test.nz.co.example.dev.testsupport.Fixtures.NONE;
import static test.nz.co.example.dev.testsupport.Fixtures.PRIMARY_REALM_DESCRIPTION;
import static test.nz.co.example.dev.testsupport.Fixtures.PRIMARY_REALM_ID;
import static test.nz.co.example.dev.testsupport.Fixtures.PRIMARY_REALM_PARENT_REALM;
import static test.nz.co.example.dev.testsupport.Fixtures.PRIMARY_SIP_DESCRIPTION;
import static test.nz.co.example.dev.testsupport.Fixtures.PRIMARY_SIP_PORT;
import static test.nz.co.example.dev.testsupport.Fixtures.PRIMARY_UTILITY_ADDRESS;
import static test.nz.co.example.dev.testsupport.Fixtures.REALM_NETWORK_INTERFACES;
import static test.nz.co.example.dev.testsupport.Fixtures.REGION;
import static test.nz.co.example.dev.testsupport.Fixtures.REGISTERED;
import static test.nz.co.example.dev.testsupport.Fixtures.SECONDARY_REALM_DESCRIPTION;
import static test.nz.co.example.dev.testsupport.Fixtures.SECONDARY_REALM_ID;
import static test.nz.co.example.dev.testsupport.Fixtures.SECONDARY_SIP_DESCRIPTION;
import static test.nz.co.example.dev.testsupport.Fixtures.SECONDARY_SIP_PORT;
import static test.nz.co.example.dev.testsupport.Fixtures.SIP_TCP_IP_ADDRESS;
import nz.co.example.dev.domain.exception.ValidationException;
import nz.co.example.dev.domain.logic.CircuitConfigData;
import nz.co.example.dev.domain.logic.WSipLayerTwoCircuitFactory;
import nz.co.example.dev.domain.logic.temp.HardCodedCircuitConfigData;
import nz.co.example.dev.domain.model.AccessRealm;
import nz.co.example.dev.domain.model.BaseCircuit;
import nz.co.example.dev.domain.model.CircuitType;
import nz.co.example.dev.domain.model.NetworkInterface;
import nz.co.example.dev.domain.model.RegionConfig;
import nz.co.example.dev.domain.model.SipInterface;
import nz.co.example.dev.domain.model.SipPort;
import nz.co.example.dev.domain.model.SteeringPool;
import nz.co.example.dev.domain.model.TransportProtocol;
import nz.co.example.dev.domain.model.WSipLayerTwoCircuit;
import nz.co.example.dev.mvc.CircuitForm;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import test.nz.co.example.dev.testsupport.Fixtures;

public class WSipLayerTwoCircuitFactoryTest {

    private WSipLayerTwoCircuitFactory circuitFactory = new WSipLayerTwoCircuitFactory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createsTypeWhenWSipLayerTwoShouldReturnTrue() {
        // Given
        BaseCircuit baseCircuit = new BaseCircuit("tux01", "218.101.10.1", "1", "Auckland", CircuitType.W_SIP_LAYER_TWO);
        CircuitForm circuitForm = Fixtures.createCircuitForm(baseCircuit);
        // When
        boolean createsType = circuitFactory.createsType(circuitForm);
        // Then
        assertThat(createsType).isTrue();
    }

    @Test
    public void createsTypeWhenNotWSipLayerTwoShouldReturnFalse() {
        // Given
        BaseCircuit baseCircuit = new BaseCircuit("tux01", "218.101.10.1", "1", "Auckland", CircuitType.UNKNOWN);
        CircuitForm circuitForm = Fixtures.createCircuitForm(baseCircuit);
        // When
        boolean createsType = circuitFactory.createsType(circuitForm);
        // Then
        assertThat(createsType).isFalse();
    }

    @Test
    public void createsTypeWhenNullCircuitTypeShouldReturnFalse() {
        // Given
        BaseCircuit baseCircuit = new BaseCircuit("tux01", "218.101.10.1", "1", "Auckland", null);
        CircuitForm circuitForm = Fixtures.createCircuitForm(baseCircuit);
        // When
        boolean createsType = circuitFactory.createsType(circuitForm);
        // Then
        assertThat(createsType).isFalse();
    }

    @Test
    public void createWhenWSipLayerTwoShouldCreateCircuitWithCorrectNetworkInterface() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        CircuitConfigData circuitConfigData = new HardCodedCircuitConfigData();
        // When
        WSipLayerTwoCircuit circuit = circuitFactory.create(circuitForm, circuitConfigData);

        // Then
        assertThat(circuit).isNotNull();
        NetworkInterface networkInterface = circuit.getNetworkInterface();
        assertThat(networkInterface).isNotNull();
        assertThat(networkInterface.getDescription()).isEqualTo(NETWORK_INTERFACE_DESCRIPTION);
        assertThat(networkInterface.getGateway().getHostAddress()).isEqualTo(GATEWAY);
        assertThat(networkInterface.getHeartbeat().getState().getDisplayValue()).isEqualTo(DISABLED);
        assertThat(networkInterface.getIpAddress().getHostAddress()).isEqualTo(IP_ADDRESS);
        assertThat(networkInterface.getName()).isEqualTo(NETWORK_INTERFACE_NAME);
        assertThat(networkInterface.getNetMask().getHostAddress()).isEqualTo(NET_MASK);
        assertThat(networkInterface.getPrimaryUtilityAddress().getHostAddress()).isEqualTo(PRIMARY_UTILITY_ADDRESS);
        assertThat(networkInterface.getSecondaryUtilityAddress().getHostAddress()).isEqualTo(
                Fixtures.SECONDARY_UTILITY_ADDRESS);
        assertThat(networkInterface.getSubport()).isEqualTo(Fixtures.NETWORK_INTERFACE_SUB_PORT);
    }

    @Test
    public void createWhenWSipLayerTwoShouldCreateCircuitWithCorrectNetworkInterfaceWithNoGateway() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        circuitForm.setDefaultGatewayIpAddress(null);
        CircuitConfigData circuitConfigData = new HardCodedCircuitConfigData();
        // When
        WSipLayerTwoCircuit circuit = circuitFactory.create(circuitForm, circuitConfigData);

        // Then
        assertThat(circuit).isNotNull();
        NetworkInterface networkInterface = circuit.getNetworkInterface();
        assertThat(networkInterface).isNotNull();
        assertThat(networkInterface.getDescription()).isEqualTo(NETWORK_INTERFACE_DESCRIPTION);
        assertThat(networkInterface.getGateway()).isNull();
        assertThat(networkInterface.getHeartbeat().getState().getDisplayValue()).isEqualTo(DISABLED);
        assertThat(networkInterface.getIpAddress().getHostAddress()).isEqualTo(IP_ADDRESS);
        assertThat(networkInterface.getName()).isEqualTo(NETWORK_INTERFACE_NAME);
        assertThat(networkInterface.getNetMask().getHostAddress()).isEqualTo(NET_MASK);
        assertThat(networkInterface.getPrimaryUtilityAddress().getHostAddress()).isEqualTo(PRIMARY_UTILITY_ADDRESS);
        assertThat(networkInterface.getSecondaryUtilityAddress().getHostAddress()).isEqualTo(
                Fixtures.SECONDARY_UTILITY_ADDRESS);
        assertThat(networkInterface.getSubport()).isEqualTo(Fixtures.NETWORK_INTERFACE_SUB_PORT);
    }

    @Test
    public void createWhenWSipLayerTwoShouldCreateCircuitWithCorrectPrimaryAccessRealm() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        CircuitConfigData circuitConfigData = new HardCodedCircuitConfigData();
        // When
        WSipLayerTwoCircuit circuit = circuitFactory.create(circuitForm, circuitConfigData);

        // Then
        assertThat(circuit).isNotNull();
        AccessRealm primaryAccessRealm = circuit.getPrimaryAccessRealm();
        assertThat(primaryAccessRealm).isNotNull();
        assertThat(primaryAccessRealm.getAccessControlTrustLevel().getDisplayValue()).isEqualTo(MEDIUM);
        assertThat(primaryAccessRealm.getAddressPrefix().getHostAddress()).isEqualTo("0.0.0.0");
        assertThat(primaryAccessRealm.getAverageRateLimit()).isEqualTo(0);
        assertThat(primaryAccessRealm.getBwCacNonMm().getDisplayValue()).isEqualTo(DISABLED);
        assertThat(primaryAccessRealm.getDescription()).isEqualTo(PRIMARY_REALM_DESCRIPTION);
        assertThat(primaryAccessRealm.getIdentifier()).isEqualTo(PRIMARY_REALM_ID);
        assertThat(primaryAccessRealm.getInvalidSignalThreshold()).isEqualTo(1);
        assertThat(primaryAccessRealm.getMaximumSignalThreshold()).isEqualTo(4000);
        assertThat(primaryAccessRealm.getMmInNetwork().getDisplayValue()).isEqualTo(ENABLED);
        assertThat(primaryAccessRealm.getMmInRealm().getDisplayValue()).isEqualTo(ENABLED);
        assertThat(primaryAccessRealm.getMmInSameIp().getDisplayValue()).isEqualTo(DISABLED);
        assertThat(primaryAccessRealm.getMmInSystem().getDisplayValue()).isEqualTo(ENABLED);
        assertThat(primaryAccessRealm.getMsmRelease().getDisplayValue()).isEqualTo(DISABLED);
        assertThat(primaryAccessRealm.getNetworkInterfaces()).isEqualTo(REALM_NETWORK_INTERFACES);
        assertThat(primaryAccessRealm.getOptions().getOptions()).containsExactly("+sip-connect-pbx-reg=rewrite-new");
        assertThat(primaryAccessRealm.getParentRealm()).isEqualTo(PRIMARY_REALM_PARENT_REALM);
        assertThat(primaryAccessRealm.getSymmetricLatching()).isNull();
        assertThat(primaryAccessRealm.getUntrustedSignalThreshold()).isEqualTo(0);
    }

    @Test
    public void createWhenWSipLayerTwoShouldCreateCircuitWithCorrectSecondaryAccessRealm() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        CircuitConfigData circuitConfigData = new HardCodedCircuitConfigData();
        // When
        WSipLayerTwoCircuit circuit = circuitFactory.create(circuitForm, circuitConfigData);

        // Then
        assertThat(circuit).isNotNull();
        AccessRealm secondaryAccessRealm = circuit.getSecondaryAccessRealm();
        assertThat(secondaryAccessRealm).isNotNull();
        assertThat(secondaryAccessRealm.getAccessControlTrustLevel().getDisplayValue()).isEqualTo(MEDIUM);
        assertThat(secondaryAccessRealm.getAddressPrefix().getHostAddress()).isEqualTo("0.0.0.0");
        assertThat(secondaryAccessRealm.getAverageRateLimit()).isEqualTo(0);
        assertThat(secondaryAccessRealm.getBwCacNonMm().getDisplayValue()).isEqualTo(DISABLED);
        assertThat(secondaryAccessRealm.getDescription()).isEqualTo(SECONDARY_REALM_DESCRIPTION);
        assertThat(secondaryAccessRealm.getIdentifier()).isEqualTo(SECONDARY_REALM_ID);
        assertThat(secondaryAccessRealm.getInvalidSignalThreshold()).isEqualTo(1);
        assertThat(secondaryAccessRealm.getMaximumSignalThreshold()).isEqualTo(4000);
        assertThat(secondaryAccessRealm.getMmInNetwork().getDisplayValue()).isEqualTo(ENABLED);
        assertThat(secondaryAccessRealm.getMmInRealm().getDisplayValue()).isEqualTo(ENABLED);
        assertThat(secondaryAccessRealm.getMmInSameIp().getDisplayValue()).isEqualTo(DISABLED);
        assertThat(secondaryAccessRealm.getMmInSystem().getDisplayValue()).isEqualTo(ENABLED);
        assertThat(secondaryAccessRealm.getMsmRelease().getDisplayValue()).isEqualTo(DISABLED);
        assertThat(secondaryAccessRealm.getNetworkInterfaces()).isEqualTo(REALM_NETWORK_INTERFACES);
        assertThat(secondaryAccessRealm.getOptions().getOptions()).containsExactly("+sip-connect-pbx-reg=rewrite-new");
        assertThat(secondaryAccessRealm.getParentRealm()).isEqualTo(PRIMARY_REALM_ID);
        assertThat(secondaryAccessRealm.getSymmetricLatching().getDisplayValue()).isEqualTo(ENABLED);
        assertThat(secondaryAccessRealm.getUntrustedSignalThreshold()).isEqualTo(0);
    }

    @Test
    public void createWhenWSipLayerTwoShouldCreateCircuitWithCorrectPrimarySipInterface() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        CircuitConfigData circuitConfigData = new HardCodedCircuitConfigData();
        // When
        WSipLayerTwoCircuit circuit = circuitFactory.create(circuitForm, circuitConfigData);

        // Then
        assertThat(circuit).isNotNull();
        SipInterface primarySipInterface = circuit.getPrimarySipInterface();
        assertThat(primarySipInterface).isNotNull();
        assertThat(primarySipInterface.getContactMode().getDisplayValue()).isEqualTo(NONE);
        assertThat(primarySipInterface.getDescription()).isEqualTo(PRIMARY_SIP_DESCRIPTION);
        assertThat(primarySipInterface.getMinRegExpire()).isEqualTo(3600);
        assertThat(primarySipInterface.getNatInterval()).isEqualTo(0);
        assertThat(primarySipInterface.getNatTraversal().getDisplayValue()).isEqualTo(NONE);
        assertThat(primarySipInterface.getOptions().getOptions()).containsExactly("+reg-via-key", "+reg-no-port-match");
        assertThat(primarySipInterface.getRealmId()).isEqualTo(PRIMARY_REALM_ID);
        assertThat(primarySipInterface.getRedirectAction().getDisplayValue()).isEqualTo("proxy");
        assertThat(primarySipInterface.getRegistrationCaching().getDisplayValue()).isEqualTo(ENABLED);
        assertThat(primarySipInterface.getRegistrationInterval()).isEqualTo(3600);
        assertThat(primarySipInterface.getRouteToRegistrar().getDisplayValue()).isEqualTo(ENABLED);
        assertThat(primarySipInterface.getSipImsFeature().getDisplayValue()).isEqualTo(ENABLED);
        assertThat(primarySipInterface.getState().getDisplayValue()).isEqualTo(ENABLED);
        assertThat(primarySipInterface.getStopRecurse()).containsOnly(401, 407);
        SipPort tcpSipPort = primarySipInterface.getTcpSipPort();
        assertThat(tcpSipPort.getAddress().getHostAddress()).isEqualTo(SIP_TCP_IP_ADDRESS);
        assertThat(tcpSipPort.getPort()).isEqualTo(PRIMARY_SIP_PORT);
        assertThat(tcpSipPort.getTransportProtocol()).isEqualTo(TransportProtocol.TCP);
        assertThat(primarySipInterface.getTrustMode().getDisplayValue()).isEqualTo(REGISTERED);
        SipPort udpSipPort = primarySipInterface.getUdpSipPort();
        assertThat(udpSipPort.getAddress().getHostAddress()).isEqualTo(IP_ADDRESS);
        assertThat(udpSipPort.getPort()).isEqualTo(PRIMARY_SIP_PORT);
        assertThat(udpSipPort.getTransportProtocol()).isEqualTo(TransportProtocol.UDP);
    }

    @Test
    public void createWhenWSipLayerTwoShouldCreateCircuitWithCorrectSecondarySipInterface() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        CircuitConfigData circuitConfigData = new HardCodedCircuitConfigData();
        // When
        WSipLayerTwoCircuit circuit = circuitFactory.create(circuitForm, circuitConfigData);

        // Then
        assertThat(circuit).isNotNull();
        SipInterface secondarySipInterface = circuit.getSecondarySipInterface();
        assertThat(secondarySipInterface).isNotNull();
        assertThat(secondarySipInterface.getContactMode().getDisplayValue()).isEqualTo(NONE);
        assertThat(secondarySipInterface.getDescription()).isEqualTo(SECONDARY_SIP_DESCRIPTION);
        assertThat(secondarySipInterface.getMinRegExpire()).isEqualTo(3600);
        assertThat(secondarySipInterface.getNatInterval()).isEqualTo(30);
        assertThat(secondarySipInterface.getNatTraversal().getDisplayValue()).isEqualTo(ALWAYS);
        assertThat(secondarySipInterface.getOptions().getOptions()).containsExactly("+reg-via-key",
                "+reg-no-port-match");
        assertThat(secondarySipInterface.getRealmId()).isEqualTo(SECONDARY_REALM_ID);
        assertThat(secondarySipInterface.getRedirectAction().getDisplayValue()).isEqualTo("proxy");
        assertThat(secondarySipInterface.getRegistrationCaching().getDisplayValue()).isEqualTo(ENABLED);
        assertThat(secondarySipInterface.getRegistrationInterval()).isEqualTo(3600);
        assertThat(secondarySipInterface.getRouteToRegistrar().getDisplayValue()).isEqualTo(ENABLED);
        assertThat(secondarySipInterface.getSipImsFeature().getDisplayValue()).isEqualTo(ENABLED);
        assertThat(secondarySipInterface.getState().getDisplayValue()).isEqualTo(ENABLED);
        assertThat(secondarySipInterface.getStopRecurse()).containsOnly(401, 407);
        assertThat(secondarySipInterface.getTcpSipPort()).isNull();
        assertThat(secondarySipInterface.getTrustMode().getDisplayValue()).isEqualTo(REGISTERED);
        SipPort udpSipPort = secondarySipInterface.getUdpSipPort();
        assertThat(udpSipPort.getAddress().getHostAddress()).isEqualTo(IP_ADDRESS);
        assertThat(udpSipPort.getPort()).isEqualTo(SECONDARY_SIP_PORT);
        assertThat(udpSipPort.getTransportProtocol()).isEqualTo(TransportProtocol.UDP);
    }

    @Test
    public void createWhenWSipLayerTwoShouldCreateCircuitWithCorrectSteeringPool() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        CircuitConfigData circuitConfigData = new HardCodedCircuitConfigData();
        // When
        WSipLayerTwoCircuit circuit = circuitFactory.create(circuitForm, circuitConfigData);

        // Then
        assertThat(circuit).isNotNull();
        SteeringPool steeringPool = circuit.getSteeringPool();
        assertThat(steeringPool).isNotNull();
        assertThat(steeringPool.getIpAddress().getHostAddress()).isEqualTo(IP_ADDRESS);
        assertThat(steeringPool.getRealmId()).isEqualTo(PRIMARY_REALM_ID);
        assertThat(steeringPool.getStartPort()).isEqualTo(16384);
        assertThat(steeringPool.getEndPort()).isEqualTo(17384);
    }

    @Test
    public void createNetworkInterfaceWhenInvalidIpShouldThrowException() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        CircuitConfigData circuitConfigData = new HardCodedCircuitConfigData();

        thrown.expect(ValidationException.class);
        thrown.expectMessage("Invalid IP Address - 10.1.1.300.");
        // Invalid IP Range
        circuitForm.setIpAddress("10.1.1.300");
        // When
        circuitFactory.createNetworkInterface(circuitForm, circuitConfigData);

    }

    @Test
    public void createPrimarySipInterfaceWhenInvalidIpShouldThrowException() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        CircuitConfigData circuitConfigData = new HardCodedCircuitConfigData();

        NetworkInterface networkInterface = circuitFactory.createNetworkInterface(circuitForm, circuitConfigData);
        AccessRealm primaryAccessRealm = circuitFactory.createPrimaryAccessRealm(circuitForm, circuitConfigData,
                networkInterface);

        thrown.expect(ValidationException.class);
        thrown.expectMessage("Invalid TCP Sip Port Address - 10.1.1.300.");

        // Invalid IP Range
        RegionConfig regionConfig = new RegionConfig(REGION, "218.101.10.7", "10.1.1.300", "m01-access");
        circuitConfigData.getRegionConfigMap().put(REGION, regionConfig);

        // When
        circuitFactory.createPrimarySipInterface(circuitForm, circuitConfigData, primaryAccessRealm);

    }

    @Test
    public void createSecondarySipInterfaceWhenInvalidIpShouldThrowException() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        CircuitConfigData circuitConfigData = new HardCodedCircuitConfigData();

        NetworkInterface networkInterface = circuitFactory.createNetworkInterface(circuitForm, circuitConfigData);
        AccessRealm primaryAccessRealm = circuitFactory.createPrimaryAccessRealm(circuitForm, circuitConfigData,
                networkInterface);

        thrown.expect(ValidationException.class);
        thrown.expectMessage("Invalid UDP Sip Port Address - 10.1.1.300.");

        // Invalid IP Range
        circuitForm.setIpAddress("10.1.1.300");

        // When
        circuitFactory.createSecondarySipInterface(circuitForm, circuitConfigData, primaryAccessRealm);

    }

    @Test
    public void createcreateSteeringPoolWhenInvalidIpShouldThrowException() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        CircuitConfigData circuitConfigData = new HardCodedCircuitConfigData();

        NetworkInterface networkInterface = circuitFactory.createNetworkInterface(circuitForm, circuitConfigData);
        AccessRealm primaryAccessRealm = circuitFactory.createPrimaryAccessRealm(circuitForm, circuitConfigData,
                networkInterface);

        thrown.expect(ValidationException.class);
        thrown.expectMessage("Invalid IP Address - 10.1.1.300.");

        // Invalid IP Range
        circuitForm.setIpAddress("10.1.1.300");

        // When
        circuitFactory.createSteeringPool(circuitForm, primaryAccessRealm);

    }
}
