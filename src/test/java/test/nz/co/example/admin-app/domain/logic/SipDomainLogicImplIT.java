package test.nz.co.example.dev.domain.logic;

import static org.junit.Assert.assertTrue;

import java.util.List;

import nz.co.example.dev.domain.logic.SipDomainLogic;
import nz.co.example.dev.domain.model.Circuit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml" })
// @Ignore
public class SipDomainLogicImplIT {
    @Autowired
    private SipDomainLogic sipDomainLogic;

    @Test
    public void testGetAllCircuits() throws Exception {
        List<Circuit> allCircuits = sipDomainLogic.getAllCircuits();
        assertTrue(!allCircuits.isEmpty());
    }
}
