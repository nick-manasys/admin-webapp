package it.nz.co.example.dev.integration.calls;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import nz.co.example.dev.domain.logic.CircuitSubComponents;
import nz.co.example.dev.integration.calls.GetAllCircuitSubComponents;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml" })
// @Ignore
public class GetAllCircuitSubComponentsIT {
    @Autowired
    private GetAllCircuitSubComponents getAllCircuitSubComponents;

    @Test
    public void testCall() throws Exception {
        CircuitSubComponents circuitSubComponents = getAllCircuitSubComponents.call();
        assertNotNull("circuitSubComponents not null", circuitSubComponents);
        assertTrue(!circuitSubComponents.isEmpty());
    }
}
