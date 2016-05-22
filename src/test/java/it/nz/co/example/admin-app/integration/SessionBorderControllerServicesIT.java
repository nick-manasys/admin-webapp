package it.nz.co.example.dev.integration;

import static org.junit.Assert.assertNotNull;
import nz.co.example.dev.domain.logic.CircuitSubComponents;
import nz.co.example.dev.domain.model.Circuit;
import nz.co.example.dev.integration.SessionBorderControllerServices;
import nz.co.example.dev.integration.SessionBorderControllerServicesImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml" })
@Ignore
public class SessionBorderControllerServicesIT {
    @Autowired
    private SessionBorderControllerServices service;

    private Circuit circuit;

    @Before
    public void setUp() throws Exception {
        service = new SessionBorderControllerServicesImpl();
    }

    @After
    public void tearDown() throws Exception {
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
