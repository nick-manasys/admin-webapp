/**
 * 
 */
package nz.co.example.dev.domain.logic;

import java.net.InetAddress;
import java.net.UnknownHostException;

import nz.co.example.dev.domain.exception.ValidationException;
import nz.co.example.dev.domain.model.AccessRealm;
import nz.co.example.dev.domain.model.CircuitType;
import nz.co.example.dev.domain.model.NetworkInterface;
import nz.co.example.dev.domain.model.RegionConfig;
import nz.co.example.dev.domain.model.SipInterface;
import nz.co.example.dev.domain.model.SteeringPool;
import nz.co.example.dev.domain.model.WSipLayerTwoCircuit;
import nz.co.example.dev.mvc.CircuitForm;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Factory capable of creating a W-SIP Layer Two circuit from the config data and the form data.
 */
@Service
public class WSipLayerTwoCircuitFactory implements CircuitFactory<WSipLayerTwoCircuit> {

    @Override
    public boolean createsType(CircuitForm circuitForm) {
        return CircuitType.W_SIP_LAYER_TWO.name().equals(circuitForm.getCircuitType());
    }

    @Override
    public WSipLayerTwoCircuit create(CircuitForm circuitForm, CircuitConfigData circuitConfigData) {
        NetworkInterface networkInterface = createNetworkInterface(circuitForm, circuitConfigData);
        AccessRealm primaryAccessRealm = createPrimaryAccessRealm(circuitForm, circuitConfigData, networkInterface);
        AccessRealm secondaryAccessRealm = createSecondaryAccessRealm(circuitForm, circuitConfigData, networkInterface,
                primaryAccessRealm);
        SipInterface primarySipInterface = createPrimarySipInterface(circuitForm, circuitConfigData, primaryAccessRealm);

        SipInterface secondarySipInterface = createSecondarySipInterface(circuitForm, circuitConfigData,
                secondaryAccessRealm);
        SteeringPool steeringPool = createSteeringPool(circuitForm, primaryAccessRealm);
        WSipLayerTwoCircuit circuit = new WSipLayerTwoCircuit(networkInterface, primaryAccessRealm,
                secondaryAccessRealm, primarySipInterface, secondarySipInterface, steeringPool, circuitForm.getRegion());

        return circuit;
    }

    public NetworkInterface createNetworkInterface(CircuitForm circuitForm, CircuitConfigData circuitConfigData) {
        RegionConfig regionConfig = circuitConfigData.getRegionConfigMap().get(circuitForm.getRegion());
        String networkInterfaceName = regionConfig.getAccessNetworkInterfaceLayerTwo();
        String networkInterfaceDescription = String.format(circuitConfigData.getNetworkInterfaceDescription(),
                circuitForm.getCarrierName());

        int networkInterfaceSubPort = Integer.parseInt(circuitForm.getAccessVLan());
        InetAddress ipAddress = parseIpAddress("IP Address", circuitForm.getIpAddress());
        InetAddress primaryUtilityAddress = parseIpAddress("Primary Utility Address",
                circuitForm.getPrimaryUtilityIpAddress());
        InetAddress secondaryUtilityAddress = parseIpAddress("Secondary Utility Address",
                circuitForm.getSecondaryUtilityIpAddress());
        InetAddress netMask = parseIpAddress("Net Mask", circuitForm.getNetworkMask());
        if (!StringUtils.hasLength(circuitForm.getDefaultGatewayIpAddress())) {
            return NetworkInterface.createWithoutGateway(networkInterfaceName, networkInterfaceSubPort,
                    networkInterfaceDescription, ipAddress, primaryUtilityAddress, secondaryUtilityAddress, netMask);
        } else {
            InetAddress gateway = parseIpAddress("Gateway", circuitForm.getDefaultGatewayIpAddress());
            return NetworkInterface.createWithGateway(networkInterfaceName, networkInterfaceSubPort,
                    networkInterfaceDescription, ipAddress, primaryUtilityAddress, secondaryUtilityAddress, netMask,
                    gateway);
        }
    }

    private InetAddress parseIpAddress(String fieldName, String addressString) {
        try {
            return InetAddress.getByName(addressString);
        } catch (UnknownHostException e) {
            throw new ValidationException(String.format("Invalid %s - %s.", fieldName, addressString), e);
        }
    }

    public AccessRealm createPrimaryAccessRealm(CircuitForm circuitForm, CircuitConfigData circuitConfigData,
            NetworkInterface networkInterface) {
        String id = String.format(circuitConfigData.getPrimaryAccessRealmId(), circuitForm.getCarrierShortCode(),
                circuitForm.getTrunkNumber());
        String description = String.format(circuitConfigData.getPrimaryAccessRealmDescription(),
                circuitForm.getCarrierName());
        String parentRealm = circuitConfigData.getPrimaryAccessRealmParentRealm();
        return AccessRealm.createPrimary(id, description, networkInterface.getId(), parentRealm,
                circuitForm.getRegion());
    }

    private AccessRealm createSecondaryAccessRealm(CircuitForm circuitForm, CircuitConfigData circuitConfigData,
            NetworkInterface networkInterface, AccessRealm primaryAccessRealm) {
        String id = String.format(circuitConfigData.getSecondaryAccessRealmId(), circuitForm.getCarrierShortCode(),
                circuitForm.getTrunkNumber());
        String description = String.format(circuitConfigData.getSecondaryAccessRealmDescription(),
                circuitForm.getCarrierName());
        String parentRealm = primaryAccessRealm.getIdentifier();
        return AccessRealm.createSecondary(id, description, networkInterface.getId(), parentRealm,
                circuitForm.getRegion());
    }

    public SipInterface createPrimarySipInterface(CircuitForm circuitForm, CircuitConfigData circuitConfigData,
            AccessRealm primaryAccessRealm) {
        RegionConfig regionConfig = circuitConfigData.getRegionConfigMap().get(circuitForm.getRegion());
        String description = String.format(circuitConfigData.getPrimarySipInterfaceDescription(),
                circuitForm.getCarrierName());
        InetAddress tcpSipPortAddress = parseIpAddress("TCP Sip Port Address", regionConfig.getSipDefinitionTwo());
        InetAddress udpSipPortAddress = parseIpAddress("UDP Sip Port Address", circuitForm.getIpAddress());
        return SipInterface.createPrimary(primaryAccessRealm.getIdentifier(), description, udpSipPortAddress,
                tcpSipPortAddress);
    }

    public SipInterface createSecondarySipInterface(CircuitForm circuitForm, CircuitConfigData circuitConfigData,
            AccessRealm seondaryAccessRealm) {
        String description = String.format(circuitConfigData.getSecondarySipInterfaceDescription(),
                circuitForm.getCarrierName());
        InetAddress udpSipPortAddress = parseIpAddress("UDP Sip Port Address", circuitForm.getIpAddress());
        return SipInterface.createSecondary(seondaryAccessRealm.getIdentifier(), description, udpSipPortAddress);

    }

    public SteeringPool createSteeringPool(CircuitForm circuitForm, AccessRealm primaryAccessRealm) {
        String realmId = primaryAccessRealm.getIdentifier();
        InetAddress ipAddress = parseIpAddress("IP Address", circuitForm.getIpAddress());
        int startPort = 16384;
        int endPort = 17384;
        SteeringPool result = SteeringPool.create(ipAddress, realmId, startPort, endPort);
        return result;
    }
}
