package nz.co.example.dev.integration.calls;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import nz.co.example.dev.domain.logic.CircuitConfigData;
import nz.co.example.dev.domain.model.NetworkInterface;
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
 * required to retrieve all of the network interfaces from the
 * dev management server.
 * 
 * @author nivanov
 * 
 */
// @Transactional(propagation = Propagation.REQUIRED)
@Service
public class GetAllNetworkInterfaces {
    @Autowired
    private NNCService nncService;

    @Autowired
    private CircuitConfigData circuitConfigData;

    public static final String NETWORK_INTERFACES = "networkInterface";
    private static final Logger logger = LoggerFactory.getLogger(GetAllNetworkInterfaces.class);

    // @Async
    public Future<List<NetworkInterface>> call() {
        List<NetworkInterface> networkInterfaceList = retrieveNetworkInterfaces();
        AsyncResult<List<NetworkInterface>> result = new AsyncResult<List<NetworkInterface>>(networkInterfaceList);
        return result;
    }

    private List<NetworkInterface> retrieveNetworkInterfaces() {
        logger.info("Retrieving all network interfaces");

        List<NetworkInterface> result = new ArrayList<NetworkInterface>();
        if ("".equals(nncService.getTargetName())) {
            // loop over all regions
            for (String targetName : nncService.getTargetNames()) {
                result.addAll(retrieveNetworkInterfacesForRegion(targetName));
            }
        } else {
            // just region
            result.addAll(retrieveNetworkInterfacesForRegion(nncService.getTargetName()));
        }
        return result;
    }

    private List<NetworkInterface> retrieveNetworkInterfacesForRegion(String targetName) {
        List<NetworkInterface> result = new ArrayList<NetworkInterface>();
        try {
            List<WSConfigElement> list = nncService.getConfigMgmtIF().getAllConfigElements(targetName,
                    NETWORK_INTERFACES);

            for (WSConfigElement element : list) {
                WSConfigElement fullElement = nncService.getConfigMgmtIF().getConfigElement(targetName, element);
                if (logger.isDebugEnabled()) {
                    WSConfigElementUtility.printElement(fullElement);
                }
                NetworkInterface item = NetworkInterface.fromWSConfigElement(fullElement);
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
