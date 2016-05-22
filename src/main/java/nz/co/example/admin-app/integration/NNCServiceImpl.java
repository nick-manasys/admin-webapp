package nz.co.example.dev.integration;

import java.net.Authenticator;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import com.acmepacket.ems.common.SaveDeviceTaskMessage;
import com.acmepacket.ems.ws.service.adminmgmt.AdminManager;
import com.acmepacket.ems.ws.service.adminmgmt.AdminMgmtIF;
import com.acmepacket.ems.ws.service.configmgmt.ConfigMgmtIF;
import com.acmepacket.ems.ws.service.devicemgmt.DeviceMgmtIF;
import com.acmepacket.ems.ws.service.fault.AcmeAdminWSFault;
import com.acmepacket.ems.ws.service.fault.AcmeDeviceWSFault;
import com.acmepacket.ems.ws.service.fault.AcmeWSFault;

/**
 * @nivanov
 * 
 */
// @Service
// @Lazy(true)
public class NNCServiceImpl implements NNCService {
    private static final Logger logger = LoggerFactory.getLogger(NNCServiceImpl.class);

    private static NNCService instance;

    private AdminManager adminManager;

    private AdminMgmtIF adminMgmtIF;

    private ConfigMgmtIF configMgmtIF;

    private DeviceMgmtIF deviceMgmtIF;

    /**
     * NNC server address.<br/>
     * e.g.</br>
     * nnc.server.address=10.66.4.9
     */
    @Value("${nnc.server.address}")
    private String nncServerAddress;

    /**
     * NNC server port.<br/>
     * e.g.</br>
     * nnc.server.port=8080
     */
    @Value("${nnc.server.port}")
    private String nncServerPort;

    /**
     * NNC server username.<br/>
     * e.g.</br>
     * nnc.server.username=devuser
     */
    @Value("${nnc.server.username}")
    private String nncServerUsername;

    /**
     * NNC server password.<br/>
     * e.g.</br>
     * nnc.server.password=******
     */
    @Value("${nnc.server.password}")
    private String nncServerPassword;

    /**
     * Trust store password.<br/>
     * e.g.</br>
     * trust.store.password=******
     */
    @Value("${trust.store.password}")
    private String trustStorePassword;

    /**
     * Default target name.<br/>
     * e.g.</br>
     * target.name=dev-LAB
     */
    @Value("${target.name:}")
    private String targetName;

    /**
     * HTTP proxy host.<br/>
     * e.g.</br>
     * http.proxy.host=10.200.15.17
     */
    @Value("${http.proxy.host}")
    private String httpProxyHost;

    /**
     * HTTP proxy port.<br/>
     * e.g.</br>
     * http.proxy.port=8080
     */
    @Value("${http.proxy.port}")
    private String httpProxyPort;

    /**
     * Proxy authentication username.<br/>
     * e.g.</br>
     * proxy.authentication.username=nivanov
     */
    @Value("${proxy.authentication.username}")
    private String proxyAuthenticationUsername;

    /**
     * Proxy authentication password.<br/>
     * e.g.</br>
     * proxy.authentication.password=******
     */
    @Value("${proxy.authentication.password}")
    private String proxyAuthenticationPassword;

    /**
     * HTTP non proxy hosts.<br/>
     * e.g.</br>
     * http.non.proxy.hosts=localhost|127.0.0.1
     */
    @Value("${http.non.proxy.hosts}")
    private String httpNonProxyHosts;

    private List<String> targetNames;

    private boolean available = false;

    /**
     * @throws Exception
     */
    public NNCServiceImpl() {
        NNCServiceImpl.instance = this;
        available = false;
    }

    /**
     * Send a request to save the configuration.
     */
    protected void saveConfig(String targetDevice) {
        try {
            logger.debug(">> NNCServiceImpl.saveConfig()");
            SaveDeviceTaskMessage saveResult = getDeviceMgmtIF().saveConfig(targetDevice);
            if (saveResult != null) {
                logger.debug("saveResult.getOverallStatus= " + saveResult.getOverallStatus());
            }

        } catch (AcmeWSFault e) {
            String errorMessage = "AcmeWSFault, Error : " + e.getValidation();
            logger.debug(errorMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Run initialization after the service instance is built.
     * 
     * @throws AcmeAdminWSFault
     */
    @PostConstruct
    public void postConstruct() throws AcmeAdminWSFault {
        tryConnection();
    }

    private void tryConnection() {
        adminManager = null;
        adminMgmtIF = null;
        configMgmtIF = null;
        deviceMgmtIF = null;

        try {
            if (StringUtils.hasLength(getHttpProxyHost())) {
                String httpProxyHost = getHttpProxyHost();

                String httpProxyPort = getHttpProxyPort();
                String proxyUsername = getProxyAuthenticationUsername();
                String proxyPassword = getProxyAuthenticationPassword();
                String httpNonProxyHosts = getHttpNonProxyHosts();
                System.setProperty("http.proxyHost", httpProxyHost);
                System.setProperty("http.proxyPort", httpProxyPort);
                System.setProperty("proxy.authentication.username", proxyUsername);
                System.setProperty("proxy.authentication.password", proxyPassword);
                System.setProperty("http.nonProxyHosts", httpNonProxyHosts);
                Authenticator.setDefault(new ProxyAuthenticator(proxyUsername, proxyPassword));
                logger.info("\nProxy configured\nhttpProxyHost=" + httpProxyHost + "\nhttpProxyPort=" + httpProxyPort
                        + "\nproxyUsername=" + proxyUsername + "\nproxyPassword=" + "****" + "\nhttpNonProxyHosts="
                        + httpNonProxyHosts);
            } else {
                logger.info("\nNo proxy configured\n");
            }
            if ("8443".equals(nncServerPort)) {
                // FIXME is this the right test for https?
                System.setProperty("trustStorePassword", trustStorePassword);

                // FIXME should these be properties?
                System.setProperty("javax.net.ssl.keyStore", System.getProperty("catalina.home") + "/conf/nnc.store");
                System.setProperty("javax.net.ssl.keyStorePassword", "pa$$w0rd");
                System.setProperty("javax.net.ssl.trustStore", System.getProperty("catalina.home") + "/conf/nnc.store");
                System.setProperty("javax.net.ssl.trustStorePassword", "pa$$w0rd");
            }
            adminManager = new AdminManager(getNncServerAddress(), getNncServerPort());

            // log in
            String username = getNncServerUsername();
            String password = getNncServerPassword();
            logger.info("\nLogging into NNC server \nusername='" + username + "'\npassword='" + "****");
            adminMgmtIF = adminManager.login(getNncServerUsername(), getNncServerPassword());
            configMgmtIF = adminManager.createConfigMgmtIF();
            deviceMgmtIF = adminManager.createDeviceMgmtIF();
            available = true;
        } catch (Exception e) {
            available = false;
            logger.debug(e.getMessage());
        }
    }

    private String getHttpNonProxyHosts() {
        return httpNonProxyHosts;
    }

    private String getProxyAuthenticationPassword() {
        return proxyAuthenticationPassword;
    }

    private String getProxyAuthenticationUsername() {
        return proxyAuthenticationUsername;
    }

    public String getHttpProxyHost() {
        return httpProxyHost;
    }

    public void setHttpProxyHost(String httpProxyHost) {
        this.httpProxyHost = httpProxyHost;
    }

    private String getHttpProxyPort() {
        return httpProxyPort;
    }

    public void setHttpProxyPort(String httpProxyPort) {
        this.httpProxyPort = httpProxyPort;
    }

    public String getNncServerUsername() {
        return nncServerUsername;
    }

    public String getNncServerPassword() {
        return nncServerPassword;
    }

    public String getNncServerPort() {
        return nncServerPort;
    }

    public String getNncServerAddress() {
        return nncServerAddress;
    }

    public void setNncServerAddress(String nncServerAddress) {
        this.nncServerAddress = nncServerAddress;
    }

    public static NNCService getInstance() {
        return instance;
    }

    public AdminManager getAdminManager() {
        if (null == adminManager) {
            // tryConnection();
        }
        return adminManager;
    }

    public void setAdminManager(AdminManager adminManager) {
        this.adminManager = adminManager;
    }

    public AdminMgmtIF getAdminMgmtIF() {
        if (null == adminMgmtIF) {
            // tryConnection();
        }
        return adminMgmtIF;
    }

    public void setAdminMgmtIF(AdminMgmtIF adminMgmtIF) {
        this.adminMgmtIF = adminMgmtIF;
    }

    public ConfigMgmtIF getConfigMgmtIF() {
        if (null == configMgmtIF) {
            // tryConnection();
        }
        return configMgmtIF;
    }

    public void setConfigMgmtIF(ConfigMgmtIF configMgmtIF) {
        this.configMgmtIF = configMgmtIF;
    }

    public DeviceMgmtIF getDeviceMgmtIF() {
        if (null == deviceMgmtIF) {
            // tryConnection();
        }
        return deviceMgmtIF;
    }

    public void setDeviceMgmtIF(DeviceMgmtIF deviceMgmtIF) {
        this.deviceMgmtIF = deviceMgmtIF;
    }

    @Override
    public String getTargetName() {
        if (null == targetName) {
            targetName = "";
        }
        return targetName;
    }

    @Override
    public List<String> getTargetNames() {
        if (null == targetNames) {
            targetNames = new ArrayList<String>();
            try {
                targetNames = deviceMgmtIF.getAllManagedDeviceTargetNames();
            } catch (AcmeDeviceWSFault e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (AcmeAdminWSFault e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return targetNames;
    }

    public List<String> getGroupNames() {
        List<String> result = new ArrayList<String>();

        try {
            result = getDeviceMgmtIF().getAllDeviceGroupList();
        } catch (AcmeDeviceWSFault e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AcmeAdminWSFault e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }
}
