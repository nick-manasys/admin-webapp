package it.nz.co.example.dev.integration;

import static org.junit.Assert.assertTrue;
import nz.co.example.dev.integration.NNCService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.acmepacket.ems.ws.service.adminmgmt.AdminManager;
import com.acmepacket.ems.ws.service.adminmgmt.AdminMgmtIF;
import com.acmepacket.ems.ws.service.configmgmt.ConfigMgmtIF;
import com.acmepacket.ems.ws.service.devicemgmt.DeviceMgmtIF;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml" })
public class NNCServiceIT {
    // @Mock
    private AdminManager adminManager;

    // @Mock
    private AdminMgmtIF adminMgmtIF;

    // @Mock
    private ConfigMgmtIF configMgmtIF;

    // @Mock
    private DeviceMgmtIF deviceMgmtIF;

    @Autowired
    private NNCService service;

    @Before
    public void setUp() {
        // EMPTY
    }

    @Test
    public void testNothing() {
        // EMPTY
    }

    @Test
    public void testGetTargetNames() throws Exception {
        assertTrue(0 != service.getTargetNames().size());
    }
}
