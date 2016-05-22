package nz.co.example.dev.integration;

import java.util.List;

import com.acmepacket.ems.ws.service.adminmgmt.AdminMgmtIF;
import com.acmepacket.ems.ws.service.configmgmt.ConfigMgmtIF;
import com.acmepacket.ems.ws.service.devicemgmt.DeviceMgmtIF;

/**
 * NNC Service is used to communicate with the NNC Server.
 * 
 * @author nivanov
 * 
 */
public interface NNCService {
    boolean isAvailable();

    AdminMgmtIF getAdminMgmtIF();

    ConfigMgmtIF getConfigMgmtIF();

    DeviceMgmtIF getDeviceMgmtIF();

    String getTargetName();

    List<String> getTargetNames();

    List<String> getGroupNames();
}