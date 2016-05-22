package test.nz.co.example.dev.integration;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nz.co.example.dev.domain.logic.CircuitSubComponents;
import nz.co.example.dev.domain.model.Circuit;
import nz.co.example.dev.domain.model.CircuitType;
import nz.co.example.dev.integration.SessionBorderControllerServices;
import nz.co.example.dev.integration.SessionBorderControllerServicesImpl;
import nz.co.example.dev.integration.calls.GetAllCircuitSubComponents;
import nz.co.example.dev.mvc.CircuitForm;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

// @RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml" })
@RunWith(MockitoJUnitRunner.class)
@Ignore
public class SessionBorderControllerServicesTest {
    @Mock
    private GetAllCircuitSubComponents netNetCentralClient;

    @Spy
    private SessionBorderControllerServices service = new SessionBorderControllerServicesImpl();

    private Circuit circuit;

    private CircuitForm circuitForm;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(service);
        stub(service.getAllCircuitSubComponents()).toReturn(null);
        when(service.getAllCircuitSubComponents()).thenReturn(null);

        circuitForm = new CircuitForm();
        circuitForm.setCircuitType(CircuitType.W_SIP_LAYER_TWO.name());
        circuitForm.setCarrierShortCode("EREAMBLRA5");
        circuitForm.setCarrierName("Erewhon");
        circuitForm.setRegion("lab");
        circuitForm.setTrunkNumber("01");
        circuitForm.setIpAddress("192.168.1.202");
        circuitForm.setPrimaryUtilityIpAddress("192.168.11.202");
        circuitForm.setSecondaryUtilityIpAddress("192.168.12.202");
        circuitForm.setNetworkMask("192.168.1.202");
        circuitForm.setAccessVLan("9999");
        circuitForm.setDefaultGatewayIpAddress("");

        // verify
        verify(netNetCentralClient).call();
    }

    @After
    public void tearDown() throws Exception {
        // EMPTY
    }

    @Test
    public final void testGetAllCircuitSubComponents() throws Exception {
        CircuitSubComponents circuitSubComponents = service.getAllCircuitSubComponents();
        assertNotNull(circuitSubComponents);
    }

    @Test
    public final void testAddNewCircuit() throws Exception {
        service.addNewCircuit(circuit);

        // SUCCESS
    }

    @Test
    public final void testModifyCircuit() throws Exception {
        service.addNewCircuit(circuit);
    }

    @Test
    public final void testRelinquishCircuit() throws Exception {
        service.addNewCircuit(circuit);
    }
}
