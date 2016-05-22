package it.nz.co.example.dev.integration.calls;

import static org.junit.Assert.assertNotSame;

import java.util.List;
import java.util.concurrent.Future;

import nz.co.example.dev.domain.model.AccessRealm;
import nz.co.example.dev.integration.calls.GetAllAccessRealms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml" })
public class GetAllAccessRealmsIT {

    @Autowired
    private GetAllAccessRealms getAllAccessRealms;

    @Test
    public void testCall() throws Exception {
        Future<List<AccessRealm>> callResult = getAllAccessRealms.call();
        List<AccessRealm> accessRealms = callResult.get();
        // assertEquals("Number of access realms", 36, accessRealms.size());
        assertNotSame("Number of access realms", 0, accessRealms.size());
    }
}
