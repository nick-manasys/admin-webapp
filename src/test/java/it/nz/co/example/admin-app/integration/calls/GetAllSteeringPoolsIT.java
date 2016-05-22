package it.nz.co.example.dev.integration.calls;

import static org.junit.Assert.assertNotSame;

import java.util.List;
import java.util.concurrent.Future;

import nz.co.example.dev.domain.model.SteeringPool;
import nz.co.example.dev.integration.calls.GetAllSteeringPools;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml" })
// @Ignore
public class GetAllSteeringPoolsIT {

    @Autowired
    private GetAllSteeringPools getAllSteeringPools;

    @Test
    public void testCall() throws Exception {
        Future<List<SteeringPool>> callResult = getAllSteeringPools.call();
        List<SteeringPool> result = callResult.get();
        assertNotSame("Number of steering pools", 0, result.size());
    }
}
