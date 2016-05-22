package nz.co.example.dev.integration.calls;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import nz.co.example.dev.domain.logic.CircuitConfigData;
import nz.co.example.dev.domain.model.SteeringPool;
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
 * required to retrieve all of the steering pools from the
 * dev management server.
 * 
 * @author nivanov
 * 
 */
@Service
public class GetAllSteeringPools {

    @Autowired
    private NNCService nncService;

    @Autowired
    private CircuitConfigData circuitConfigData;

    public static final String STEERING_POOLS = "steeringPool";

    private static final Logger logger = LoggerFactory.getLogger(GetAllSteeringPools.class);

    // @Async
    public Future<List<SteeringPool>> call() {
        List<SteeringPool> emptyList = retrieveSteeringPools();
        AsyncResult<List<SteeringPool>> result = new AsyncResult<List<SteeringPool>>(emptyList);
        return result;
    }

    private List<SteeringPool> retrieveSteeringPools() {
        logger.info("Retrieving all steering pools");

        List<SteeringPool> result = new ArrayList<SteeringPool>();

        if ("".equals(nncService.getTargetName())) {
            // loop over all regions
            for (String targetName : nncService.getTargetNames()) {
                result.addAll(retrieveSteeringPoolsForRegion(targetName));
            }
        } else {
            // just region
            result.addAll(retrieveSteeringPoolsForRegion(nncService.getTargetName()));
        }
        return result;
    }

    /**
     * @param targetName
     * @return
     */
    private List<SteeringPool> retrieveSteeringPoolsForRegion(String targetName) {
        List<SteeringPool> result = new ArrayList<SteeringPool>();
        try {
            List<WSConfigElement> list = nncService.getConfigMgmtIF().getAllConfigElements(targetName, STEERING_POOLS);

            for (WSConfigElement element : list) {
                WSConfigElement fullElement = nncService.getConfigMgmtIF().getConfigElement(targetName, element);
                SteeringPool item = SteeringPool.fromWSConfigElement(fullElement);
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
