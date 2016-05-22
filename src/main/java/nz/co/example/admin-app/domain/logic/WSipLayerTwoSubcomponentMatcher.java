package nz.co.example.dev.domain.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import nz.co.example.dev.domain.model.AccessRealm;
import nz.co.example.dev.domain.model.NetworkInterface;
import nz.co.example.dev.domain.model.SipInterface;
import nz.co.example.dev.domain.model.SteeringPool;
import nz.co.example.dev.domain.model.WSipLayerTwoCircuit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This is a factory class capable of taking a list of various circuit
 * subcomponents and sorting the subcomponents into matching {@link WSipLayerTwoCircuit} items.
 * 
 * A {@link WSipLayerTwoCircuit} consists of:
 * 
 * A {@link NetworkInterface} A primary {@link AccessRealm} A secondary {@link AccessRealm} A primary
 * {@link SipInterface} A secondary {@link SipInterface} A {@link SteeringPool}
 * 
 * @author nivanov
 * 
 */
@Service
public class WSipLayerTwoSubcomponentMatcher implements CircuitSubcomponentMatcher<WSipLayerTwoCircuit> {
    private static final Logger logger = LoggerFactory.getLogger(WSipLayerTwoSubcomponentMatcher.class);

    @Autowired
    private CircuitConfigData circuitConfigData;

    /**
     * Matches the given subcomponents together and creates the list of {@link WSipLayerTwoCircuit} types.
     * 
     * The returned SortedCircuitResult has the sorted circuits, and the left
     * over subcomponents that could not be sorted.
     * 
     * @param subComponents
     * @return
     */
    @Override
    public SortedCircuitResult<WSipLayerTwoCircuit> matchSubComponents(CircuitSubComponents subComponents) {
        SortedCircuitResult<WSipLayerTwoCircuit> sortedCircuitResult = new SortedCircuitResult<WSipLayerTwoCircuit>();
        List<WSipLayerTwoCircuit> sortedCircuits = new ArrayList<WSipLayerTwoCircuit>();

        ConcurrentHashMap<String, NetworkInterface> networkInterfaces = new ConcurrentHashMap<String, NetworkInterface>();
        networkInterfaces.putAll(subComponents.getNetworkInterfacesMappedById());
        Map<String, AccessRealm> accessRealms = subComponents.getAccessRealmsMappedByNetworkInterfaceIdAndOrder();
        Map<String, SipInterface> sipInterfaces = subComponents.getSipInterfacesMappedByRealmId();
        Map<String, SteeringPool> steeringPools = subComponents.getSteeringPoolsMappedByRealmId();

        for (AccessRealm secondaryAccessRealm : subComponents.getAccessRealm()) {
            NetworkInterface networkInterface = null;
            SipInterface primarySipInterface = null;
            SipInterface secondarySipInterface = null;
            SteeringPool steeringPool = null;

            if (secondaryAccessRealm.getIdentifier().endsWith("n.acc")) {
                String secondaryRealmId = secondaryAccessRealm.getIdentifier();
                String primaryRealmId = secondaryAccessRealm.getIdentifier().substring(0,
                        secondaryRealmId.length() - 1 - ".acc".length())
                        + ".acc";
                for (AccessRealm primaryAccessRealm : subComponents.getAccessRealm()) {
                    if (primaryRealmId.equals(primaryAccessRealm.getIdentifier())) {
                        for (String networkInterfaceId : networkInterfaces.keySet()) {
                            if (secondaryAccessRealm.getNetworkInterfaces().equals(networkInterfaceId)) {
                                networkInterface = networkInterfaces.get(networkInterfaceId);
                            }
                        }
                        primarySipInterface = sipInterfaces.get(primaryAccessRealm.getIdentifier());
                        steeringPool = steeringPools.get(primaryAccessRealm.getIdentifier());
                        secondarySipInterface = sipInterfaces.get(secondaryAccessRealm.getIdentifier());

                        WSipLayerTwoCircuit wSipLayerTwoCircuit = createCircuit(networkInterface, primaryAccessRealm,
                                secondaryAccessRealm, primarySipInterface, secondarySipInterface, steeringPool);

                        if (wSipLayerTwoCircuit != null) {
                            sortedCircuits.add(wSipLayerTwoCircuit);
                        } else {
                            logger.debug("Not adding candidate pair " + networkInterface + " " + primaryRealmId + ", "
                                    + secondaryRealmId + ", " + primarySipInterface + ", " + secondarySipInterface
                                    + ", " + steeringPool);
                        }
                    }
                }
            }
        }

        CircuitSubComponents unsortedSubComponents = new CircuitSubComponents(networkInterfaces, accessRealms,
                sipInterfaces, steeringPools);
        sortedCircuitResult.setUnsortedSubComponents(unsortedSubComponents);
        sortedCircuitResult.addSortedCircuits(sortedCircuits);
        return sortedCircuitResult;
    }

    /**
     * @param networkInterface
     * @param primaryAccessRealm
     * @param secondaryAccessRealm
     * @param primarySipInterface
     * @param secondarySipInterface
     * @param steeringPool
     * @return
     */
    private WSipLayerTwoCircuit createCircuit(NetworkInterface networkInterface, AccessRealm primaryAccessRealm,
            AccessRealm secondaryAccessRealm, SipInterface primarySipInterface, SipInterface secondarySipInterface,
            SteeringPool steeringPool) {
        WSipLayerTwoCircuit wSipLayerTwoCircuit = null;
        if (allSubelementsAreFound(networkInterface, primaryAccessRealm, secondaryAccessRealm, primarySipInterface,
                secondarySipInterface, steeringPool)) {
            // String sipDefinitionTwoHostAddress = WSConfigElementUtility
            // .getSipDefinitionTwoHostAddress(primarySipInterface);
            String regionName = primaryAccessRealm.getRegionCode(); // regionConfig.getName()
            wSipLayerTwoCircuit = new WSipLayerTwoCircuit(networkInterface, primaryAccessRealm, secondaryAccessRealm,
                    primarySipInterface, secondarySipInterface, steeringPool, regionName);
        }
        return wSipLayerTwoCircuit;
    }

    private boolean allSubelementsAreFound(NetworkInterface networkInterface, AccessRealm primaryAccessRealm,
            AccessRealm secondaryAccessRealm, SipInterface primarySipInterface, SipInterface secondarySipInterface,
            SteeringPool steeringPool) {
        boolean result = networkInterface != null && primaryAccessRealm != null && secondaryAccessRealm != null
                && primarySipInterface != null && secondarySipInterface != null && steeringPool != null;
        result = true;
        return result;
    }

}
