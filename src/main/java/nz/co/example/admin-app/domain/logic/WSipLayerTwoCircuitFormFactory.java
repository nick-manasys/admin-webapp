/**
 * 
 */
package nz.co.example.dev.domain.logic;

import nz.co.example.dev.domain.model.Circuit;
import nz.co.example.dev.domain.model.CircuitType;
import nz.co.example.dev.domain.model.NetworkInterface;
import nz.co.example.dev.domain.model.WSipLayerTwoCircuit;
import nz.co.example.dev.mvc.CircuitForm;

import org.springframework.stereotype.Service;

/**
 * Factory capable of creating a W-SIP Layer Two circuit form from the circuit data and the config data.
 * 
 * @author nivanov
 * 
 */
@Service
public class WSipLayerTwoCircuitFormFactory implements CircuitFormFactory<WSipLayerTwoCircuit> {

    @Override
    public boolean createsFromType(Circuit circuit) {
        return CircuitType.W_SIP_LAYER_TWO.equals(circuit.getCircuitType());
    }

    @Override
    public CircuitForm create(WSipLayerTwoCircuit circuit, CircuitConfigData circuitConfigData) {
        CircuitForm circuitForm = new CircuitForm();
        NetworkInterface networkInterface = circuit.getNetworkInterface();
        circuitForm.setAccessVLan(Integer.toString(networkInterface.getSubport()));
        circuitForm.setCarrierShortCode(circuit.getCarrierId());
        String description = networkInterface.getDescription();
        circuitForm.setCarrierName(description.substring(description.lastIndexOf(" ") + 1));
        circuitForm.setCircuitType(CircuitType.W_SIP_LAYER_TWO.name());
        if (networkInterface.getGateway() != null) {
            circuitForm.setDefaultGatewayIpAddress(networkInterface.getGateway().getHostAddress());
        }
        circuitForm.setIpAddress(networkInterface.getIpAddress().getHostAddress());
        circuitForm.setNetworkMask(networkInterface.getNetMask().getHostAddress());
        // TODO repeated code doing non null safe operation
        circuitForm.setPrimaryUtilityIpAddress(networkInterface.getPrimaryUtilityAddress().getHostAddress());
        circuitForm.setSecondaryUtilityIpAddress(networkInterface.getSecondaryUtilityAddress().getHostAddress());
        // SipInterface primarySipInterface = circuit.getPrimarySipInterface();
        // String sipDefinitionTwoHostAddress =
        // WSConfigElementUtility.getSipDefinitionTwoHostAddress(primarySipInterface);
        // circuit.getPrimarySipInterface().getTcpSipPort().getAddress()
        // .getHostAddress();
        String regionName = circuit.getPrimaryAccessRealm().getRegionCode();
        circuitForm.setRegion(regionName);

        String realmId = circuit.getPrimaryAccessRealm().getIdentifier();
        int indexOfLastDash = realmId.lastIndexOf("-") + 1;
        int indexOfLastFullStop = realmId.indexOf(".", indexOfLastDash);
        String trunkNumber = realmId.substring(indexOfLastDash, indexOfLastFullStop);
        circuitForm.setTrunkNumber(trunkNumber);

        return circuitForm;
    }
}
