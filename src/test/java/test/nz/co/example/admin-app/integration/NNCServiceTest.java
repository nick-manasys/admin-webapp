package test.nz.co.example.dev.integration;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import nz.co.example.dev.integration.NNCService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.acmepacket.ems.ws.service.adminmgmt.AdminManager;
import com.acmepacket.ems.ws.service.adminmgmt.AdminMgmtIF;
import com.acmepacket.ems.ws.service.configmgmt.ConfigMgmtIF;
import com.acmepacket.ems.ws.service.devicemgmt.DeviceMgmtIF;

@RunWith(MockitoJUnitRunner.class)
public class NNCServiceTest {
    // @Mock
    private AdminManager adminManager;

    // @Mock
    private AdminMgmtIF adminMgmtIF;

    // @Mock
    private ConfigMgmtIF configMgmtIF;

    // @Mock
    private DeviceMgmtIF deviceMgmtIF;

    @Mock
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
        List<String> targetNames = new ArrayList<String>();
        targetNames.add("dev-TEST");

        // when
        when(service.getTargetNames()).thenReturn(targetNames);

        assertTrue(0 != service.getTargetNames().size());
    }
}
