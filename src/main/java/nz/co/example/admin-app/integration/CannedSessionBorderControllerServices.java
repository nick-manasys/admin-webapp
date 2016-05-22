/**
 * 
 */
package nz.co.example.dev.integration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import nz.co.example.dev.domain.logic.CircuitConfigData;
import nz.co.example.dev.domain.logic.CircuitSubComponents;
import nz.co.example.dev.domain.logic.WSipLayerTwoCircuitFactory;
import nz.co.example.dev.domain.model.AccessRealm;
import nz.co.example.dev.domain.model.BaseCircuit;
import nz.co.example.dev.domain.model.Circuit;
import nz.co.example.dev.domain.model.CircuitType;
import nz.co.example.dev.domain.model.NetworkInterface;
import nz.co.example.dev.domain.model.SipInterface;
import nz.co.example.dev.domain.model.SteeringPool;
import nz.co.example.dev.domain.model.WSipLayerTwoCircuit;
import nz.co.example.dev.integration.calls.GetAllCircuitSubComponents;
import nz.co.example.dev.mvc.CircuitForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Canned data implementation to allow us to mock out the back-end ao that we can continue with
 * the rest of the application.
 * 
 * @author nivanov
 * 
 */
@Service
@Qualifier("canned")
public class CannedSessionBorderControllerServices implements SessionBorderControllerServices {

    private static final String SECONDARY_UTILITY_IP = "203.167.239.203";

    private static final String PRIMARY_UTILITY_IP = "203.167.239.202";

    private static final String NETWORK_MASK = "255.255.255.248";

    private static final String GATEWAY_IP = "192.168.59.254";

    @Autowired
    private WSipLayerTwoCircuitFactory circuitFactory;

    @Autowired
    private GetAllCircuitSubComponents netNetCentralClient;

    @Autowired
    private CircuitConfigData circuitConfigData;

    /*
     * (non-Javadoc)
     * 
     * @see nz.co.example.dev.integration.NetNetCentralServices#getAllCircuitSubComponents()
     */
    @Override
    public CircuitSubComponents getAllCircuitSubComponents() {
        netNetCentralClient.call();

        List<NetworkInterface> networkInterfaces = new ArrayList<NetworkInterface>();
        List<AccessRealm> accessRealms = new ArrayList<AccessRealm>();
        List<SipInterface> sipInterfaces = new ArrayList<SipInterface>();
        List<SteeringPool> steeringPools = new ArrayList<SteeringPool>();
        for (int i = 100; i < 250; i++) {
            WSipLayerTwoCircuit circuit = createCircuit(i);
            networkInterfaces.add(circuit.getNetworkInterface());
            accessRealms.addAll(Arrays.asList(circuit.getPrimaryAccessRealm(), circuit.getSecondaryAccessRealm()));
            sipInterfaces.addAll(Arrays.asList(circuit.getPrimarySipInterface(), circuit.getSecondarySipInterface()));
            steeringPools.add(circuit.getSteeringPool());
        }
        CircuitSubComponents circuitSubComponents = new CircuitSubComponents(networkInterfaces, accessRealms,
                sipInterfaces, steeringPools);
        return circuitSubComponents;
    }

    public List<Circuit> getAllCircuits() {
        List<Circuit> circuits = new ArrayList<Circuit>();
        for (int i = 100; i < 250; i++) {
            circuits.add(createCircuit(i));
        }
        return circuits;
    }

    private WSipLayerTwoCircuit createCircuit(int num) {
        Random rnd = new Random();
        int mod = rnd.nextInt(4);
        String carrierId = "tux";
        switch (mod) {
        case 1:
            carrierId = "dux";
            break;
        case 2:
            carrierId = "bux";
            break;
        case 3:
            carrierId = "mux";
            break;

        default:
            carrierId = "tux";
            break;
        }

        BaseCircuit baseCircuit = new BaseCircuit(carrierId + num, "218.101.10." + num, "" + num, mod % 2 == 0 ? "wlgn"
                : "auc", CircuitType.W_SIP_LAYER_TWO);
        CircuitForm circuitForm = createCircuitForm(baseCircuit);
        return circuitFactory.create(circuitForm, circuitConfigData);
    }

    public CircuitForm createCircuitForm(Circuit circuit) {
        CircuitForm circuitForm = new CircuitForm();
        circuitForm.setCircuitType("W_SIP_LAYER_TWO");
        circuitForm.setAccessVLan(circuit.getVLAN());
        circuitForm.setIpAddress(circuit.getIpAddress());
        circuitForm.setCarrierName(circuit.getCarrierId().substring(0, 1).toUpperCase()
                + circuit.getCarrierId().substring(1, 3));
        circuitForm.setCarrierShortCode(circuit.getCarrierId());
        circuitForm.setDefaultGatewayIpAddress(GATEWAY_IP);
        circuitForm.setNetworkMask(NETWORK_MASK);
        circuitForm.setPrimaryUtilityIpAddress(PRIMARY_UTILITY_IP);
        circuitForm.setSecondaryUtilityIpAddress(SECONDARY_UTILITY_IP);
        circuitForm.setTrunkNumber("02");
        circuitForm.setValidatedReadyForSave(false);
        circuitForm.setRegion(circuit.getRegion());
        return circuitForm;
    }

    @Override
    public void addNewCircuit(Circuit circuit) {
        // Do Nothing in this implementation.
    }

    @Override
    public void modifyCircuit(Circuit oldircuit, Circuit newCircuit) {
        // Do Nothing in this implementation.
    }

    @Override
    public void relinquishCircuit(Circuit circuit) {
        // Do Nothing in this implementation.
    }

    @Override
    public String getLoginBanner() {
        return "";
    }
}
