package nz.co.example.dev.integration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nz.co.example.dev.domain.logic.CircuitConfigData;
import nz.co.example.dev.domain.logic.CircuitSubComponents;
import nz.co.example.dev.domain.logic.WSipLayerTwoCircuitFactory;
import nz.co.example.dev.domain.model.BaseCircuit;
import nz.co.example.dev.domain.model.Circuit;
import nz.co.example.dev.domain.model.CircuitType;
import nz.co.example.dev.domain.model.WSipLayerTwoCircuit;
import nz.co.example.dev.integration.calls.AddWSipLayerTwoCircuit;
import nz.co.example.dev.integration.calls.GetAllCircuitSubComponents;
import nz.co.example.dev.integration.calls.RemoveWSipLayerTwoCircuit;
import nz.co.example.dev.integration.operation.Operation;
import nz.co.example.dev.mvc.CircuitForm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.acmepacket.ems.common.SaveDeviceTaskMessage;
import com.acmepacket.ems.ws.service.fault.AcmeAdminWSFault;
import com.acmepacket.ems.ws.service.fault.AcmeDeviceWSFault;

/**
 * Service which processes high level commands.
 */
@Service
// @Lazy(true)
@Qualifier("real")
// @Transactional(rollbackFor = { NullPointerException.class }, timeout = 10, propagation = Propagation.REQUIRED)
public class SessionBorderControllerServicesImpl implements SessionBorderControllerServices {
    private static Logger logger = LoggerFactory.getLogger(SessionBorderControllerServicesImpl.class);

    private static Logger operationsLog = LoggerFactory.getLogger("operations");

    @Autowired
    private NNCService nncService;

    @Autowired
    private WSipLayerTwoCircuitFactory circuitFactory;

    @Autowired
    private CircuitConfigData circuitConfigData;

    @Autowired
    private GetAllCircuitSubComponents netNetCentralClient;

    @Autowired
    private AddWSipLayerTwoCircuit addWSipLayerTwoCircuit;

    @Autowired
    private RemoveWSipLayerTwoCircuit removeWSipLayerTwoCircuit;

    @Override
    // @Transactional(readOnly = true)
    public String getLoginBanner() {
        logger.info("info");
        // operationsLog.info("\n\nOperation: getting login banner\n\n");
        String result;
        result = "nncService.getAdminMgmtIF().getLoginBanner()";
        return result;
    }

    @Override
    // @Transactional(readOnly = true)
    public CircuitSubComponents getAllCircuitSubComponents() {
        CircuitSubComponents circuitSubComponents = null;
        logger.info("Operation: GET ALL CIRCUITSUBCOMPONENTS");

        try {
            circuitSubComponents = netNetCentralClient.call();

            /*
             * List<NetworkInterface> networkInterfaces = circuitSubComponents.getNetworkInterfaces();
             * 
             * List<AccessRealm> accessRealms = circuitSubComponents.getAccessRealm();
             * List<SipInterface> sipInterfaces = circuitSubComponents.getSipInterfaces();
             * List<SteeringPool> steeringPools = circuitSubComponents.getSteeringPools();
             * 
             * for (int i = 100; i < 250; i++) {
             * WSipLayerTwoCircuit circuit = createCircuit(i);
             * networkInterfaces.add(circuit.getNetworkInterface());
             * accessRealms.addAll(Arrays.asList(circuit.getPrimaryAccessRealm(), circuit.getSecondaryAccessRealm()));
             * sipInterfaces.addAll(Arrays.asList(circuit.getPrimarySipInterface(),
             * circuit.getSecondarySipInterface()));
             * steeringPools.add(circuit.getSteeringPool());
             * }
             * CircuitSubComponents circuitSubComponents = new CircuitSubComponents(networkInterfaces, accessRealms,
             * sipInterfaces, steeringPools);
             */
            Operation operation = new nz.co.example.dev.integration.operation.GetAllWSipLayerTwoCircuits();
            LogEntry logEntry = new LogEntry();
            logEntry.setOperation(operation);
            operationsLog.info(LogEntry.toString(logEntry));
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return circuitSubComponents;
    }

    /**
     * @param num
     * @return
     */
    public WSipLayerTwoCircuit createCircuit(int num) {
        Random rnd = new Random();
        int mod = rnd.nextInt(4);
        String carrierId = "tux";

        BaseCircuit baseCircuit = new BaseCircuit(carrierId + num, "218.101.10." + num, "" + num, mod % 2 == 0 ? "wlgn"
                : "auc", CircuitType.W_SIP_LAYER_TWO);
        CircuitForm circuitForm = createCircuitForm(baseCircuit);
        return circuitFactory.create(circuitForm, circuitConfigData);
    }

    /**
     * @param circuit
     * @return
     */
    public CircuitForm createCircuitForm(Circuit circuit) {
        // FIXME
        CircuitForm circuitForm = new CircuitForm();
        circuitForm.setCircuitType("W_SIP_LAYER_TWO");
        circuitForm.setAccessVLan(circuit.getVLAN());
        circuitForm.setIpAddress(circuit.getIpAddress());
        circuitForm.setCarrierName(circuit.getCarrierId().substring(0, 1).toUpperCase()
                + circuit.getCarrierId().substring(1, 3));
        circuitForm.setCarrierShortCode(circuit.getCarrierId());
        // TODO circuitForm.setDefaultGatewayIpAddress(GATEWAY_IP);
        // TODO circuitForm.setNetworkMask(NETWORK_MASK);
        // TODO circuitForm.setPrimaryUtilityIpAddress(PRIMARY_UTILITY_IP);
        // TODO circuitForm.setSecondaryUtilityIpAddress(SECONDARY_UTILITY_IP);
        circuitForm.setDefaultGatewayIpAddress("255.255.255.255");
        circuitForm.setNetworkMask("255.255.255.255");
        circuitForm.setPrimaryUtilityIpAddress("255.255.255.255");
        circuitForm.setSecondaryUtilityIpAddress("255.255.255.255");
        circuitForm.setTrunkNumber("02");
        circuitForm.setValidatedReadyForSave(false);
        circuitForm.setRegion(circuit.getRegion());
        return circuitForm;
    }

    // @Transactional(rollbackFor = { NullPointerException.class }, timeout = 3)
    @Override
    // SipDomainLogicTest
    public void addNewCircuit(Circuit circuit) {
        logger.info("\n\n\nAdding circuit " + circuit.getKey() + "\n\n");
        try {
            // FIXME check if it is right type with instanceof
            WSipLayerTwoCircuit cc = (WSipLayerTwoCircuit) circuit;
            Operation operation = new nz.co.example.dev.integration.operation.AddWSipLayerTwoCircuit(cc);
            LogEntry logEntry = new LogEntry();
            logEntry.setOperation(operation);
            IntegrationCallResult result = addWSipLayerTwoCircuit.call(cc);
            SaveDeviceTaskMessage saveDeviceTaskMessage = nncService.getDeviceMgmtIF().saveConfig("dev-LAB");
            logger.debug("Result " + result.toString());
            logger.debug(saveDeviceTaskMessage.getDeviceName());
            logger.debug("Create " + saveDeviceTaskMessage.getIsCreateSuccess());
            logger.debug("SaveConfig " + saveDeviceTaskMessage.getIsSaveConfigSuccess());
            logger.debug("ActiveConfig " + saveDeviceTaskMessage.getIsActivateConfigSuccess());
            logger.debug(saveDeviceTaskMessage.toString());
            operationsLog.info(LogEntry.toString(logEntry));
        } catch (AcmeDeviceWSFault e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AcmeAdminWSFault e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        // throw new NullPointerException("Test transaction exception");
    }

    @Override
    public void modifyCircuit(Circuit oldCircuit, Circuit newCircuit) {
        logger.info("\n\n\nModifying old circuit " + oldCircuit.getKey() + "\n\n");
        logger.info("\n\n\nModifying new circuit " + newCircuit.getKey() + "\n\n");

        try {
            this.relinquishCircuit(oldCircuit);

            this.addNewCircuit(newCircuit);

            logger.info("\n\n\nModification complete " + oldCircuit.getKey() + "\n" + newCircuit.getKey() + "\n\n");
            List<Object> operands = new ArrayList<Object>();
            operands.add(oldCircuit);
            operands.add(newCircuit);
            Operation operation = new nz.co.example.dev.integration.operation.ModifyWSipLayerTwoCircuit(operands);
            LogEntry logEntry = new LogEntry();
            logEntry.setOperation(operation);
            operationsLog.info(LogEntry.toString(logEntry));
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    @Override
    public void relinquishCircuit(Circuit circuit) {
        logger.info("\n\n\nRelinquishing product " + circuit.getKey() + "\n\n" + circuit.toString()
                + "\n\n\n\n\nRelinquishing product " + circuit.getKey());

        try {
            // FIXME check if it is right type with instanceof
            WSipLayerTwoCircuit cc = (WSipLayerTwoCircuit) circuit;
            IntegrationCallResult result = removeWSipLayerTwoCircuit.call(cc);
            SaveDeviceTaskMessage saveDeviceTaskMessage = nncService.getDeviceMgmtIF().saveConfig("dev-LAB");
            logger.debug(saveDeviceTaskMessage.getDeviceName());
            logger.debug("Create " + saveDeviceTaskMessage.getIsCreateSuccess());
            logger.debug("SaveConfig " + saveDeviceTaskMessage.getIsSaveConfigSuccess());
            logger.debug("ActiveConfig " + saveDeviceTaskMessage.getIsActivateConfigSuccess());
            logger.debug(saveDeviceTaskMessage.toString());
            Operation operation = new nz.co.example.dev.integration.operation.DeleteWSipLayerTwoCircuit(cc);
            LogEntry logEntry = new LogEntry();
            logEntry.setOperation(operation);
            operationsLog.info(LogEntry.toString(logEntry));
        } catch (AcmeDeviceWSFault e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AcmeAdminWSFault e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        // throw new NullPointerException("Test transaction exception");
    }
}
