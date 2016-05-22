/**
 * 
 */
package test.nz.co.example.dev.domain.logic;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import nz.co.example.dev.domain.logic.CircuitConfigData;
import nz.co.example.dev.domain.logic.CircuitSubComponents;
import nz.co.example.dev.domain.logic.SortedCircuitResult;
import nz.co.example.dev.domain.logic.WSipLayerTwoCircuitFactory;
import nz.co.example.dev.domain.logic.WSipLayerTwoSubcomponentMatcher;
import nz.co.example.dev.domain.model.AccessRealm;
import nz.co.example.dev.domain.model.NetworkInterface;
import nz.co.example.dev.domain.model.SipInterface;
import nz.co.example.dev.domain.model.SteeringPool;
import nz.co.example.dev.domain.model.WSipLayerTwoCircuit;
import nz.co.example.dev.integration.calls.GetAllCircuitSubComponents;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Nick
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml" })
public class WSipLayerTwoSubcomponentMatcherIT {
    @InjectMocks
    private WSipLayerTwoSubcomponentMatcher subcomponentMatcher;

    @Autowired
    private CircuitConfigData circuitConfigData;

    @Autowired
    private GetAllCircuitSubComponents getAllCircuitSubComponents;

    @Autowired
    private WSipLayerTwoSubcomponentMatcher matcher;

    // private HardCodedCircuitConfigData hardCodedCircuitConfigData = new HardCodedCircuitConfigData();

    private WSipLayerTwoCircuitFactory circuitFactory = new WSipLayerTwoCircuitFactory();

    private List<NetworkInterface> networkInterfaces = new ArrayList<NetworkInterface>();
    private List<AccessRealm> accessRealms = new ArrayList<AccessRealm>();
    private List<SipInterface> sipInterfaces = new ArrayList<SipInterface>();
    private List<SteeringPool> steeringPools = new ArrayList<SteeringPool>();

    @Before
    public void setUp() {
        // EMPTY
    }

    @Test
    public void testMatcherShouldResolveSuccessfully() {
        CircuitSubComponents circuitSubComponents = getAllCircuitSubComponents.call();
        SortedCircuitResult<WSipLayerTwoCircuit> sortedCircuitResult = matcher.matchSubComponents(circuitSubComponents);
        assertTrue(!sortedCircuitResult.getSortedCircuits().isEmpty());
    }
}
