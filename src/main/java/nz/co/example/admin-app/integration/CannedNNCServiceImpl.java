package nz.co.example.dev.integration;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.jws.WebParam;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.acmepacket.ems.common.SaveDeviceTaskMessage;
import com.acmepacket.ems.ws.service.adminmgmt.AdminMgmtIF;
import com.acmepacket.ems.ws.service.configmgmt.ConfigMgmtIF;
import com.acmepacket.ems.ws.service.devicemgmt.DeviceMgmtIF;
import com.acmepacket.ems.ws.service.fault.AcmeAdminWSFault;
import com.acmepacket.ems.ws.service.fault.AcmeConfigWSFault;
import com.acmepacket.ems.ws.service.fault.AcmeDeviceWSFault;
import com.acmepacket.ems.ws.service.userobjects.AccountManagementInfo;
import com.acmepacket.ems.ws.service.userobjects.DeviceInfoObject;
import com.acmepacket.ems.ws.service.userobjects.IntegrityCheckResult;
import com.acmepacket.ems.ws.service.userobjects.NNCDetails;
import com.acmepacket.ems.ws.service.userobjects.devDetails;
import com.acmepacket.ems.ws.service.userobjects.TrapReceiver;
import com.acmepacket.ems.ws.service.userobjects.UserInfo;
import com.acmepacket.ems.ws.service.userobjects.WSBatch;
import com.acmepacket.ems.ws.service.userobjects.WSConfigAttribute;
import com.acmepacket.ems.ws.service.userobjects.WSConfigAttributeMetaData;
import com.acmepacket.ems.ws.service.userobjects.WSConfigElement;
import com.acmepacket.ems.ws.service.userobjects.WSConfigElementMetaData;
import com.acmepacket.ems.ws.service.userobjects.WSConfigResult;
import com.acmepacket.ems.ws.service.userobjects.WSDeviceResult;

@Service
// @Lazy(true)
@Qualifier("canned")
public class CannedNNCServiceImpl implements NNCService {

    private static NNCService instance;

    private String targetName;

    private List<String> targetNames;

    private List<String> groupNames;

    private AdminMgmtIF adminMgmtIF;

    private ConfigMgmtIF configMgmtIF;

    private DeviceMgmtIF deviceMgmtIF;

    public static NNCService getInstance() {
        return instance;
    }

    public CannedNNCServiceImpl() {
        instance = this;
    }

    @PostConstruct
    public void initialize() {
        targetName = "dev-LAB";

        targetNames = new ArrayList<String>();
        targetNames.add("AUC");
        targetNames.add("WLG");
        targetNames.add("CHC");
        targetNames.add("dev-LAB");

        groupNames = new ArrayList<String>();
        groupNames.add("Grp1");

        adminMgmtIF = new AdminMgmtIF() {

            @Override
            public AccountManagementInfo getAccountManagementInfo() throws AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public ArrayList<UserInfo> getAllUsersInfo() throws AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String getLoginBanner() throws AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public ArrayList<TrapReceiver> getTrapReceivers() throws AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public UserInfo getUserInfo(String arg0) throws AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public WSDeviceResult logOut() throws AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String login(@WebParam(name = "username") String arg0, @WebParam(name = "password") String arg1)
                    throws AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }
        };

        configMgmtIF = new ConfigMgmtIF() {

            @Override
            public WSConfigResult addConfigElement(@WebParam(name = "targetName") String arg0,
                    @WebParam(name = "wsConfigElement") WSConfigElement arg1) throws AcmeConfigWSFault,
                    AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public WSConfigResult addSubElement(@WebParam(name = "targetName") String arg0,
                    @WebParam(name = "parent") WSConfigElement arg1, @WebParam(name = "child") WSConfigElement arg2)
                    throws AcmeConfigWSFault, AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public WSConfigResult applyBatch(@WebParam(name = "targetName") String arg0,
                    @WebParam(name = "soapBatch") WSBatch arg1) throws AcmeConfigWSFault, AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public WSConfigResult deleteConfigElement(@WebParam(name = "targetName") String arg0,
                    @WebParam(name = "wsConfigElement") WSConfigElement arg1) throws AcmeConfigWSFault,
                    AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public WSConfigResult deleteSubElement(@WebParam(name = "targetName") String arg0,
                    @WebParam(name = "parent") WSConfigElement arg1, @WebParam(name = "child") WSConfigElement arg2)
                    throws AcmeConfigWSFault, AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String encryptedPassword(@WebParam(name = "configurationPasswordInfo") String arg0,
                    @WebParam(name = "inputPassword") String arg1) throws AcmeConfigWSFault, AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public ArrayList<WSConfigAttribute> getACLItoACPMappingList(@WebParam(name = "targetName") String arg0)
                    throws AcmeConfigWSFault, AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public ArrayList<WSConfigElement> getAllConfigElements(@WebParam(name = "targetName") String arg0,
                    @WebParam(name = "elementType") String arg1) throws AcmeConfigWSFault, AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public ArrayList<WSConfigAttributeMetaData> getAllSupportedAttributeInfoByElementType(
                    @WebParam(name = "targetName") String arg0, @WebParam(name = "elementType") String arg1)
                    throws AcmeConfigWSFault, AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public WSConfigAttributeMetaData getConfigAttributeMetaData(@WebParam(name = "targetName") String arg0,
                    @WebParam(name = "elementType") String arg1, @WebParam(name = "attributeName") String arg2)
                    throws AcmeConfigWSFault, AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public WSConfigElement getConfigElement(@WebParam(name = "targetName") String arg0,
                    @WebParam(name = "wsConfigElement") WSConfigElement arg1) throws AcmeConfigWSFault,
                    AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public WSConfigElementMetaData getConfigElementMetaData(@WebParam(name = "targetName") String arg0,
                    @WebParam(name = "elementType") String arg1) throws AcmeConfigWSFault, AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public ArrayList<String> getPrimaryKeyByElementType(@WebParam(name = "targetName") String arg0,
                    @WebParam(name = "elementType") String arg1) throws AcmeConfigWSFault, AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public ArrayList<String> getRequiredSubElementsByElementType(@WebParam(name = "targetName") String arg0,
                    @WebParam(name = "elementType") String arg1) throws AcmeConfigWSFault, AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public ArrayList<String> getSubElementTypesByElementType(@WebParam(name = "targetName") String arg0,
                    @WebParam(name = "elementType") String arg1) throws AcmeConfigWSFault, AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public ArrayList<String> getTopLevelConfigElementTypeNames(@WebParam(name = "targetName") String arg0)
                    throws AcmeConfigWSFault, AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public ArrayList<String> getValuesForReferenceAttribute(@WebParam(name = "targetName") String arg0,
                    @WebParam(name = "elementType") String arg1, @WebParam(name = "attributeName") String arg2)
                    throws AcmeAdminWSFault, AcmeConfigWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public WSConfigElement newConfigElement(@WebParam(name = "targetName") String arg0,
                    @WebParam(name = "elementType") String arg1) throws AcmeConfigWSFault, AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public WSConfigResult replace(@WebParam(name = "targetName") String arg0,
                    @WebParam(name = "wsConfigElement") WSConfigElement arg1) throws AcmeConfigWSFault,
                    AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public WSConfigResult updateConfigElement(@WebParam(name = "targetName") String arg0,
                    @WebParam(name = "wsConfigElement") WSConfigElement arg1) throws AcmeConfigWSFault,
                    AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

        };

        deviceMgmtIF = new DeviceMgmtIF() {

            @Override
            public SaveDeviceTaskMessage activateConfig(@WebParam(name = "targetName") String arg0)
                    throws AcmeDeviceWSFault, AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public WSDeviceResult addDevice(@WebParam(name = "deviceInfoObject") DeviceInfoObject arg0)
                    throws AcmeDeviceWSFault, AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public boolean addDeviceGroup(@WebParam(name = "deviceGroupPath") String arg0) throws AcmeDeviceWSFault,
                    AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public WSConfigResult addDeviceToEMLicense(@WebParam(name = "targetName") String arg0)
                    throws AcmeAdminWSFault, AcmeDeviceWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public WSDeviceResult deleteDevice(@WebParam(name = "targetName") String arg0) throws AcmeDeviceWSFault,
                    AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public boolean deleteDeviceGroup(@WebParam(name = "deviceGroupPath") String arg0) throws AcmeDeviceWSFault,
                    AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public WSDeviceResult deleteUserChanges(@WebParam(name = "targetName") String arg0)
                    throws AcmeDeviceWSFault, AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public ArrayList<String> getAllAssociatedDevicesInEMLicense() throws AcmeAdminWSFault, AcmeDeviceWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public ArrayList<String> getAllDeviceGroupList() throws AcmeDeviceWSFault, AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public ArrayList<String> getAllManagedDeviceTargetNames() throws AcmeDeviceWSFault, AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public ArrayList<devDetails> getAllManagedDevicesByDeviceGroup(
                    @WebParam(name = "devicetGroupPath") String arg0) throws AcmeDeviceWSFault, AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public ArrayList<String> getAllManagedDevicesNames() throws AcmeDeviceWSFault, AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public int getDevicePollingInterval() throws AcmeAdminWSFault, AcmeDeviceWSFault {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public ArrayList<WSConfigElement> getLCVContentSaveSessionReport(
                    @WebParam(name = "targetName") String arg0, @WebParam(name = "userName") String arg1)
                    throws AcmeDeviceWSFault, AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public NNCDetails getNNCDetails() throws AcmeDeviceWSFault, AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public devDetails getdevDetails(@WebParam(name = "targetName") String arg0) throws AcmeDeviceWSFault,
                    AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public ArrayList<IntegrityCheckResult> getTopLevelElementCount(@WebParam(name = "targetName") String arg0)
                    throws AcmeDeviceWSFault, AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public WSDeviceResult loadDevice(@WebParam(name = "targetName") String arg0) throws AcmeDeviceWSFault,
                    AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public WSDeviceResult lockDevice(@WebParam(name = "targetName") String arg0) throws AcmeDeviceWSFault,
                    AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public WSConfigResult removeDeviceFromEMLicense(@WebParam(name = "targetName") String arg0)
                    throws AcmeAdminWSFault, AcmeDeviceWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public SaveDeviceTaskMessage saveAndActivateConfig(@WebParam(name = "targetName") String arg0)
                    throws AcmeDeviceWSFault, AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public SaveDeviceTaskMessage saveConfig(@WebParam(name = "targetName") String arg0)
                    throws AcmeDeviceWSFault, AcmeAdminWSFault {
                SaveDeviceTaskMessage result = new SaveDeviceTaskMessage();
                return result;
            }

            @Override
            public WSDeviceResult unlockDevice(@WebParam(name = "targetName") String arg0) throws AcmeDeviceWSFault,
                    AcmeAdminWSFault {
                // TODO Auto-generated method stub
                return null;
            }
        };
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public AdminMgmtIF getAdminMgmtIF() {
        return adminMgmtIF;
    }

    @Override
    public ConfigMgmtIF getConfigMgmtIF() {
        return configMgmtIF;
    }

    @Override
    public DeviceMgmtIF getDeviceMgmtIF() {
        return deviceMgmtIF;
    }

    @Override
    public String getTargetName() {
        return targetName;
    }

    @Override
    public List<String> getTargetNames() {
        return targetNames;
    }

    @Override
    public List<String> getGroupNames() {
        return groupNames;
    }
}
