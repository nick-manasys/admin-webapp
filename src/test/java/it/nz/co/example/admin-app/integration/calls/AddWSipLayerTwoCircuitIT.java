package it.nz.co.example.dev.integration.calls;

import static org.junit.Assert.assertNotNull;
import nz.co.example.dev.domain.logic.CircuitConfigData;
import nz.co.example.dev.domain.logic.WSipLayerTwoCircuitFactory;
import nz.co.example.dev.domain.logic.temp.ConfigurationData;
import nz.co.example.dev.domain.model.CircuitType;
import nz.co.example.dev.domain.model.WSipLayerTwoCircuit;
import nz.co.example.dev.integration.IntegrationCallResult;
import nz.co.example.dev.integration.calls.AddSipInterface;
import nz.co.example.dev.integration.calls.AddWSipLayerTwoCircuit;
import nz.co.example.dev.integration.calls.RemoveWSipLayerTwoCircuit;
import nz.co.example.dev.mvc.CircuitForm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Nick
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml" })
public class AddWSipLayerTwoCircuitIT {
    @Autowired
    private AddSipInterface addSipInterface;

    @Autowired
    AddWSipLayerTwoCircuit addWSipLayerTwoCircuit;

    @Autowired
    RemoveWSipLayerTwoCircuit removeWSipLayerTwoCircuit;

    @Autowired
    private WSipLayerTwoCircuitFactory circuitFactory;

    @Before
    public void setUp() throws Exception {
        // EMPTY
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testCall() throws Exception {
        //
        CircuitForm circuitForm = new CircuitForm();
        circuitForm.setCircuitType(CircuitType.W_SIP_LAYER_TWO.name());
        circuitForm.setCarrierShortCode("EREAMBLRA5");
        circuitForm.setCarrierName("Erewhon");
        circuitForm.setRegion("lab");
        circuitForm.setTrunkNumber("01");
        circuitForm.setIpAddress("192.168.1.202");
        circuitForm.setPrimaryUtilityIpAddress("192.168.11.202");
        circuitForm.setSecondaryUtilityIpAddress("192.168.12.202");
        circuitForm.setNetworkMask("192.168.1.202");
        circuitForm.setAccessVLan("3999");
        circuitForm.setDefaultGatewayIpAddress("");

        //
        CircuitConfigData circuitConfigData = new ConfigurationData();
        WSipLayerTwoCircuit circuit = circuitFactory.create(circuitForm, circuitConfigData);

        //
        IntegrationCallResult result;

        result = addWSipLayerTwoCircuit.call(circuit);
        assertNotNull(result);

        result = removeWSipLayerTwoCircuit.call(circuit);
        assertNotNull(result);

        // call get and see if it is still there
    }
}
