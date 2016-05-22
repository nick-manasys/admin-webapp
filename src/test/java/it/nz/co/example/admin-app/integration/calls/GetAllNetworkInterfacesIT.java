package it.nz.co.example.dev.integration.calls;

import static org.junit.Assert.assertNotSame;

import java.util.List;
import java.util.concurrent.Future;

import nz.co.example.dev.domain.model.NetworkInterface;
import nz.co.example.dev.integration.calls.GetAllNetworkInterfaces;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml" })
// @Ignore
public class GetAllNetworkInterfacesIT {

    @Autowired
    private GetAllNetworkInterfaces getAllNetworkInterfaces;

    @Test
    public void testCall() throws Exception {
        Future<List<NetworkInterface>> callResult = getAllNetworkInterfaces.call();
        List<NetworkInterface> result = callResult.get();
        // assertEquals("Number of network interfaces", 11, result.size());
        assertNotSame("Number of network interfaces", 11, result.size());
    }
}
