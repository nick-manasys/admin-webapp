package test.nz.co.example.dev.testsupport;

import static nz.co.example.dev.domain.model.CircuitType.W_SIP_LAYER_TWO;

import java.net.InetAddress;
import java.net.UnknownHostException;

import nz.co.example.dev.domain.model.AccessRealm;
import nz.co.example.dev.domain.model.AnonymousMode;
import nz.co.example.dev.domain.model.BaseCircuit;
import nz.co.example.dev.domain.model.Circuit;
import nz.co.example.dev.domain.model.NetworkInterface;
import nz.co.example.dev.domain.model.SipInterface;
import nz.co.example.dev.domain.model.SipPort;
import nz.co.example.dev.domain.model.SteeringPool;
import nz.co.example.dev.domain.model.TransportProtocol;
import nz.co.example.dev.domain.model.WSipLayerTwoCircuit;
import nz.co.example.dev.mvc.CircuitForm;

public class Fixtures {
    private static final int STEERINGPOOL_START_PORT = 16384;
    private static final int STEERINGPOOL_END_PORT = 17384;
    public static final String MEDIUM = "medium";
    public static final String NONE = "none";
    public static final String ALWAYS = "always";
    public static final String DISABLED = "disabled";
    public static final String ENABLED = "enabled";
    public static final String REGISTERED = "registered";

    public static final String NETWORK_INTERFACE_NAME = "m01-access";
    public static final int NETWORK_INTERFACE_SUB_PORT = 889;
    public static final String NETWORK_INTERFACE_DESCRIPTION = "Connection to Access Network for customer Tux";
    public static final String PRIMARY_UTILITY_ADDRESS = "203.167.239.202";
    public static final String SECONDARY_UTILITY_ADDRESS = "203.167.239.203";
    public static final String NET_MASK = "255.255.255.248";
    public static final String GATEWAY = "192.168.50.254";

    public static final String TRUNK_NUMBER = "02";
    public static final String IP_ADDRESS = "218.101.10.200";
    public static final String CARRIER_ID = "tux";
    public static final String CARRIER_NAME = "Tux";
    public static final String VLAN = "889";
    public static final String REGION = "wlgn";

    public static final String PRIMARY_REALM_ID = "CS2K-tux-02.acc";
    public static final String PRIMARY_REALM_DESCRIPTION = "Access Realm for customer Tux";
    public static final String REALM_NETWORK_INTERFACES = NETWORK_INTERFACE_NAME + ":" + NETWORK_INTERFACE_SUB_PORT;
    public static final String PRIMARY_REALM_PARENT_REALM = "CS2K-pabx.acc";
    public static final String PRIMARY_REALM_REGION = "wlgn";

    public static final String SECONDARY_REALM_ID = "CS2K-tux-02n.acc";
    public static final String SECONDARY_REALM_DESCRIPTION = "NAT Access Realm for customer Tux";

    public static final String PRIMARY_SIP_DESCRIPTION = "Access SIP-Interface for customer Tux";
    public static final String SECONDARY_SIP_DESCRIPTION = "NAT Access SIP-Interface for customer Tux";

    public static final String SIP_TCP_IP_ADDRESS = "218.101.10.72";
    public static final int PRIMARY_SIP_PORT = 5060;
    public static final int SECONDARY_SIP_PORT = 5080;

    public static NetworkInterface createNetworkInterface() {
        try {
            return NetworkInterface.createWithGateway(NETWORK_INTERFACE_NAME, NETWORK_INTERFACE_SUB_PORT,
                    NETWORK_INTERFACE_DESCRIPTION, InetAddress.getByName(IP_ADDRESS),
                    InetAddress.getByName(PRIMARY_UTILITY_ADDRESS), InetAddress.getByName(SECONDARY_UTILITY_ADDRESS),
                    InetAddress.getByName(NET_MASK), InetAddress.getByName(GATEWAY));
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static NetworkInterface createNetworkInterface(String name, int subport) {
        try {
            return NetworkInterface.createWithGateway(name, subport, NETWORK_INTERFACE_DESCRIPTION,
                    InetAddress.getByName(IP_ADDRESS), InetAddress.getByName(PRIMARY_UTILITY_ADDRESS),
                    InetAddress.getByName(SECONDARY_UTILITY_ADDRESS), InetAddress.getByName(NET_MASK),
                    InetAddress.getByName(GATEWAY));
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static NetworkInterface createNetworkInterfaceWithoutGateway() {
        try {
            return NetworkInterface.createWithoutGateway(NETWORK_INTERFACE_NAME, NETWORK_INTERFACE_SUB_PORT,
                    NETWORK_INTERFACE_DESCRIPTION, InetAddress.getByName(IP_ADDRESS),
                    InetAddress.getByName(PRIMARY_UTILITY_ADDRESS), InetAddress.getByName(SECONDARY_UTILITY_ADDRESS),
                    InetAddress.getByName(NET_MASK));
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static AccessRealm createPrimaryAccessRealm() {
        return AccessRealm.createPrimary(PRIMARY_REALM_ID, PRIMARY_REALM_DESCRIPTION, REALM_NETWORK_INTERFACES,
                PRIMARY_REALM_PARENT_REALM, PRIMARY_REALM_REGION);
    }

    public static AccessRealm createPrimaryAccessRealm(String networkInterface) {
        return AccessRealm.createPrimary(PRIMARY_REALM_ID, PRIMARY_REALM_DESCRIPTION, networkInterface,
                PRIMARY_REALM_PARENT_REALM, PRIMARY_REALM_REGION);
    }

    public static AccessRealm createSecondaryAccessRealm() {
        return AccessRealm.createSecondary(SECONDARY_REALM_ID, SECONDARY_REALM_DESCRIPTION, REALM_NETWORK_INTERFACES,
                PRIMARY_REALM_ID, PRIMARY_REALM_REGION);
    }

    public static SipInterface createPrimarySipInterface() {
        try {
            return SipInterface.createPrimary(PRIMARY_REALM_ID, PRIMARY_SIP_DESCRIPTION,
                    InetAddress.getByName(IP_ADDRESS), InetAddress.getByName(SIP_TCP_IP_ADDRESS));
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

    }

    public static SipInterface createPrimarySipInterface(String realmId) {
        try {
            return SipInterface.createPrimary(realmId, PRIMARY_SIP_DESCRIPTION, InetAddress.getByName(IP_ADDRESS),
                    InetAddress.getByName(SIP_TCP_IP_ADDRESS));
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

    }

    public static SipInterface createSecondarySipInterface() {
        try {
            return SipInterface.createSecondary(PRIMARY_REALM_ID, SECONDARY_SIP_DESCRIPTION,
                    InetAddress.getByName(IP_ADDRESS));
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static SteeringPool createSteeringPool() {
        try {
            return SteeringPool.create(InetAddress.getByName(IP_ADDRESS), PRIMARY_REALM_ID, STEERINGPOOL_START_PORT,
                    STEERINGPOOL_END_PORT);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static SipPort createPrimaryUdpSipPort() {
        try {
            return new SipPort(InetAddress.getByName(IP_ADDRESS), PRIMARY_SIP_PORT, AnonymousMode.REGISTERED,
                    TransportProtocol.UDP);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static SipPort createPrimaryTcpSipPort() {
        try {
            return new SipPort(InetAddress.getByName(SIP_TCP_IP_ADDRESS), PRIMARY_SIP_PORT, AnonymousMode.REGISTERED,
                    TransportProtocol.TCP);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static SipPort createSecondaryUdpSipPort() {
        try {
            return new SipPort(InetAddress.getByName(IP_ADDRESS), SECONDARY_SIP_PORT, AnonymousMode.REGISTERED,
                    TransportProtocol.UDP);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static CircuitForm createCircuitForm(Circuit circuit) {
        CircuitForm circuitForm = new CircuitForm();
        circuitForm.setCircuitType(circuit.getCircuitType() == null ? null : circuit.getCircuitType().name());
        circuitForm.setAccessVLan(circuit.getVLAN());
        circuitForm.setIpAddress(circuit.getIpAddress());
        circuitForm.setCarrierName(circuit.getCarrierId().substring(0, 1).toUpperCase()
                + circuit.getCarrierId().substring(1, 3));
        circuitForm.setCarrierShortCode(circuit.getCarrierId());
        circuitForm.setDefaultGatewayIpAddress(GATEWAY);
        circuitForm.setNetworkMask(NET_MASK);
        circuitForm.setPrimaryUtilityIpAddress(PRIMARY_UTILITY_ADDRESS);
        circuitForm.setSecondaryUtilityIpAddress(SECONDARY_UTILITY_ADDRESS);
        circuitForm.setTrunkNumber(TRUNK_NUMBER);
        circuitForm.setValidatedReadyForSave(false);
        circuitForm.setRegion(circuit.getRegion());
        return circuitForm;
    }

    public static CircuitForm createCircuitForm() {
        return createCircuitForm(new BaseCircuit(CARRIER_ID, IP_ADDRESS, VLAN, REGION, W_SIP_LAYER_TWO));
    }

    public static WSipLayerTwoCircuit createWSipLayerTwoCircuit() {
        WSipLayerTwoCircuit circuit = new WSipLayerTwoCircuit(createNetworkInterface(), createPrimaryAccessRealm(),
                createSecondaryAccessRealm(), createPrimarySipInterface(), createSecondarySipInterface(),
                createSteeringPool(), REGION);
        return circuit;
    }

    public static WSipLayerTwoCircuit createWSipLayerTwoCircuitWithoutGateway() {
        WSipLayerTwoCircuit circuit = new WSipLayerTwoCircuit(createNetworkInterfaceWithoutGateway(),
                createPrimaryAccessRealm(), createSecondaryAccessRealm(), createPrimarySipInterface(),
                createSecondarySipInterface(), createSteeringPool(), REGION);
        return circuit;
    }
}
