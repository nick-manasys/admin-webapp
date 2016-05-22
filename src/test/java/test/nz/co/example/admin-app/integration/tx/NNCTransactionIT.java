package test.nz.co.example.dev.integration.tx;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import nz.co.example.dev.domain.logic.CircuitConfigData;
import nz.co.example.dev.domain.logic.WSipLayerTwoCircuitFactory;
import nz.co.example.dev.domain.model.WSipLayerTwoCircuit;
import nz.co.example.dev.integration.NNCService;
import nz.co.example.dev.integration.SessionBorderControllerServices;
import nz.co.example.dev.mvc.CircuitForm;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.acmepacket.ems.ws.service.fault.AcmeAdminWSFault;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml" })
public class NNCTransactionIT {
    @Autowired
    private NNCService nncService;

    @Autowired
    @Qualifier("real")
    private SessionBorderControllerServices service;

    @Autowired
    private CircuitConfigData circuitConfigData;

    @Autowired
    private WSipLayerTwoCircuitFactory circuitFactory;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    private static CircuitForm createCircuit() {
        CircuitForm circuitForm = new CircuitForm();
        circuitForm.setCircuitType("W_SIP_LAYER_TWO");
        // FIXME ?? circuitForm.setCircuitTypeOptions(circuitForm.getCircuitTypeOptions());
        circuitForm.setCarrierShortCode("TEST01");
        circuitForm.setCarrierName("Test01");
        circuitForm.setRegion("lab");
        // FIXME ?? circuitForm.setRegionOptions(circuitForm.getRegionOptions());
        circuitForm.setTrunkNumber("01");
        circuitForm.setIpAddress("192.168.1.101");
        circuitForm.setPrimaryUtilityIpAddress("192.168.1.102");
        circuitForm.setSecondaryUtilityIpAddress("192.168.1.103");
        circuitForm.setNetworkMask("255.255.255.0");
        circuitForm.setAccessVLan("3001");
        circuitForm.setDefaultGatewayIpAddress("");

        circuitForm.setValidatedReadyForSave(circuitForm.isValidatedReadyForSave());
        return circuitForm;
    }

    @Test
    public void testNothing() {
        // SUCCESS
    }

    @Ignore
    public void createNewCircuitSaveAndActivateAndCommitTransaction() {
        // create WSipLayer2Circuit
        CircuitForm circuitForm = createCircuit();
        WSipLayerTwoCircuit circuit = circuitFactory.create(circuitForm, circuitConfigData);

        // operation
        // save and activate
        // expect no exception and verify commit has been run
        try {
            service.addNewCircuit(circuit);
            // SUCCESS
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            try {
                // remove circuit
                service.relinquishCircuit(circuit);
                // SUCCESS
            } catch (Exception e) {
                e.printStackTrace();
                fail();
            }
        }
    }

    @Ignore("Do not use this until logout, login, savieConfig and applyConfig need to be done at the same level")
    public void createNewCircuitSaveAndActivateAndRollbackTransactionByEndingSession() {
        // create WSipLayer2Circuit
        CircuitForm circuitForm = createCircuit();

        // save
        // activate
        // expect exception and verify rollback has been run
        // create WSipLayer2Circuit
        WSipLayerTwoCircuit circuit = circuitFactory.create(circuitForm, circuitConfigData);

        // operation
        // save and activate
        try {
            service.addNewCircuit(circuit);
            // SUCCESS
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        try {
            nncService.getAdminMgmtIF().logOut();
        } catch (AcmeAdminWSFault e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            // remove circuit
            service.relinquishCircuit(circuit);
            // SUCCESS
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Ignore
    public void createNewCircuitSaveAndActivateAndRollbackTransaction() {
        // create WSipLayer2Circuit
        CircuitForm circuitForm = createCircuit();

        // save
        // activate
        // expect exception and verify rollback has been run
        // create WSipLayer2Circuit
        WSipLayerTwoCircuit circuit = circuitFactory.create(circuitForm, circuitConfigData);

        // operation
        // save and activate
        try {
            service.addNewCircuit(circuit);
            // SUCCESS
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        try {
            service.addNewCircuit(circuit);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof RuntimeException);
            // SUCCESS
        }

        // remove circuit
        try {
            service.relinquishCircuit(circuit);
            // SUCCESS
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        // expect no exception and verify commit has been run

        // Given
        // given(circuitCache.isEmpty()).willReturn(true);

        // When
        // List<Circuit> allCircuits = sipDomainLogicImpl.getAllCircuits();

        // Then
        // assertThat(allCircuits.size()).isEqualTo(circuitList.size());

        // Verify
        // verify(service).addNewCircuit(circuit);
    }
}
