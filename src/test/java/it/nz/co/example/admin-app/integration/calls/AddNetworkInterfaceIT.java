package it.nz.co.example.dev.integration.calls;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.Future;

import nz.co.example.dev.domain.model.NetworkInterface;
import nz.co.example.dev.integration.IntegrationCallResult;
import nz.co.example.dev.integration.calls.AddNetworkInterface;
import nz.co.example.dev.integration.calls.RemoveNetworkInterface;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import test.nz.co.example.dev.testsupport.Fixtures;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml" })
public class AddNetworkInterfaceIT {
    @Autowired
    private AddNetworkInterface addNetworkInterface;

    @Autowired
    private RemoveNetworkInterface removeNetworkInterface;

    @Before
    public void setUp() throws Exception {
        // EMPTY
    }

    @After
    public void tearDown() throws Exception {
        // EMPTY
    }

    @Test
    public void testCall() throws Exception {
        IntegrationCallResult addNetworkInterfaceResult = null;
        IntegrationCallResult removeNetworkInterfaceResult = null;

        // save network interface
        NetworkInterface networkInterface = Fixtures.createNetworkInterfaceWithoutGateway();
        Future<IntegrationCallResult> callResult = addNetworkInterface.call(networkInterface);
        addNetworkInterfaceResult = callResult.get();
        assertTrue(!addNetworkInterfaceResult.hasErrors() && !addNetworkInterfaceResult.hasWarnings());

        try {
            // remove network interface
            Future<IntegrationCallResult> callResult2 = removeNetworkInterface.call(networkInterface);
            removeNetworkInterfaceResult = callResult2.get();
            assertTrue(!removeNetworkInterfaceResult.hasErrors() && !removeNetworkInterfaceResult.hasWarnings());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
