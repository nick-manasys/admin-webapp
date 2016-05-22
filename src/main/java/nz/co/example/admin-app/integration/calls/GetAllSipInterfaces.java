package nz.co.example.dev.integration.calls;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import nz.co.example.dev.domain.logic.CircuitConfigData;
import nz.co.example.dev.domain.model.SipInterface;
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
 * required to retrieve all of the sip interfaces from the
 * dev management server.
 * 
 * @author nivanov
 * 
 */
@Service
public class GetAllSipInterfaces {

    @Autowired
    private NNCService nncService;

    @Autowired
    private CircuitConfigData circuitConfigData;

    public static final String SIP_INTERFACES = "sipInterface";

    private static final Logger logger = LoggerFactory.getLogger(GetAllSipInterfaces.class);

    // @Async
    public Future<List<SipInterface>> call() {
        List<SipInterface> emptyList = retrievdevInterfaces();
        AsyncResult<List<SipInterface>> result = new AsyncResult<List<SipInterface>>(emptyList);
        return result;
    }

    private List<SipInterface> retrievdevInterfaces() {
        logger.info("Retrieving all sip interfaces");

        List<SipInterface> result = new ArrayList<SipInterface>();
        if ("".equals(nncService.getTargetName())) {
            // loop over all regions
            for (String targetName : nncService.getTargetNames()) {
                result.addAll(retrievdevInterfacesForRegion(targetName));
            }
        } else {
            // just region
            result.addAll(retrievdevInterfacesForRegion(nncService.getTargetName()));
        }
        return result;
    }

    private List<SipInterface> retrievdevInterfacesForRegion(String targetName) {
        List<SipInterface> result = new ArrayList<SipInterface>();
        try {
            List<WSConfigElement> list = nncService.getConfigMgmtIF().getAllConfigElements(targetName, SIP_INTERFACES);

            for (WSConfigElement e : list) {
                WSConfigElement fullElement = nncService.getConfigMgmtIF().getConfigElement(targetName, e);
                if (logger.isDebugEnabled()) {
                    WSConfigElementUtility.printElement(fullElement);
                }
                SipInterface item = SipInterface.fromWSConfigElement(fullElement);
                result.add(item);
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
