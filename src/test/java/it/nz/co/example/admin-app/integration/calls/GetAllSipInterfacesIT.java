package it.nz.co.example.dev.integration.calls;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.concurrent.Future;

import nz.co.example.dev.domain.model.SipInterface;
import nz.co.example.dev.integration.calls.GetAllSipInterfaces;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml" })
@Ignore
public class GetAllSipInterfacesIT {
    @Autowired
    private GetAllSipInterfaces getAllSipInterfaces;

    @Test
    public void testCall() throws Exception {
        Future<List<SipInterface>> callResult = getAllSipInterfaces.call();
        List<SipInterface> result = callResult.get();
        assertEquals("Number of sip interfaces", 30, result.size());
    }
}
