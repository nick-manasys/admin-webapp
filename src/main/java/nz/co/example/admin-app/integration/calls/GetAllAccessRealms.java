package nz.co.example.dev.integration.calls;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import nz.co.example.dev.domain.logic.CircuitConfigData;
import nz.co.example.dev.domain.model.AccessRealm;
import nz.co.example.dev.integration.NNCService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.acmepacket.ems.ws.service.fault.AcmeAdminWSFault;
import com.acmepacket.ems.ws.service.fault.AcmeConfigWSFault;
import com.acmepacket.ems.ws.service.userobjects.WSConfigElement;

/**
 * Provides the service call wrapped in a future
 * required to retrieve all of the access realms from the
 * dev management server.
 * 
 * 
 */
@Service
public class GetAllAccessRealms {
    private static final Logger logger = LoggerFactory.getLogger(GetAllAccessRealms.class);

    @Autowired
    private NNCService nncService;

    @Autowired
    private CircuitConfigData circuitConfigData;

    public static final String ACCESS_REALMS = "realmConfig"; // accessRealms

    // @Async
    public Future<List<AccessRealm>> call() {
        List<AccessRealm> emptyList = retrieveAccessRealms();
        AsyncResult<List<AccessRealm>> result = new AsyncResult<List<AccessRealm>>(emptyList);
        return result;
    }

    private List<AccessRealm> retrieveAccessRealms() {
        logger.info("Retrieving all access realms");

        List<AccessRealm> result = new ArrayList<AccessRealm>();
        if ("".equals(nncService.getTargetName())) {
            // loop over all regions
            for (String targetName : nncService.getTargetNames()) {
                result.addAll(retrieveAccessRealmsForRegion(targetName));
            }
        } else {
            // just region
            result.addAll(retrieveAccessRealmsForRegion(nncService.getTargetName()));
        }
        return result;
    }

    /**
     * @param targetName
     * @return
     */
    private List<AccessRealm> retrieveAccessRealmsForRegion(String targetName) {
        String regionCode = circuitConfigData.getTargetNameToRegionMap().get(targetName);
        List<AccessRealm> result = new ArrayList<AccessRealm>();
        try {
            List<WSConfigElement> list = nncService.getConfigMgmtIF().getAllConfigElements(targetName, ACCESS_REALMS);

            for (WSConfigElement e : list) {
                WSConfigElement fullElement = nncService.getConfigMgmtIF().getConfigElement(targetName, e);
                if (logger.isDebugEnabled()) {
                    WSConfigElementUtility.printElement(fullElement);
                }
                AccessRealm accessRealm = AccessRealm.fromWSConfigElement(fullElement);
                accessRealm.setRegionCode(regionCode);
                result.add(accessRealm);
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (AcmeConfigWSFault e) {
            throw new RuntimeException("Could not get all elements for target " + targetName, e);
        } catch (AcmeAdminWSFault e) {
            throw new RuntimeException("Could not get all elements for target " + targetName, e);
        }
        return result;
    }
}
