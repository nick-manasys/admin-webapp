package nz.co.example.dev.integration.calls;

import java.util.concurrent.Future;

import nz.co.example.dev.domain.logic.CircuitConfigData;
import nz.co.example.dev.domain.model.SteeringPool;
import nz.co.example.dev.integration.IntegrationCallResult;
import nz.co.example.dev.integration.NNCService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.acmepacket.ems.ws.service.fault.AcmeAdminWSFault;
import com.acmepacket.ems.ws.service.fault.AcmeConfigWSFault;
import com.acmepacket.ems.ws.service.userobjects.WSConfigElement;

/**
 * Provides the service call wrapped in a future
 * required to add a steering pool on the
 * dev management server.
 * 
 * @author nivanov
 * 
 */
// @Transactional(propagation = Propagation.REQUIRED)
@Service
public class AddSteeringPool {
    @Autowired
    private NNCService nncService;

    @Autowired
    private CircuitConfigData circuitConfigData;

    public static final String ADD_STEERING_POOL = "addSteeringPool";

    private static final Logger logger = LoggerFactory.getLogger(AddSteeringPool.class);

    // @Async
    public Future<IntegrationCallResult> call(SteeringPool steeringPool) {
        IntegrationCallResult integrationCallResult = addSteeringPool(steeringPool);
        AsyncResult<IntegrationCallResult> result = new AsyncResult<IntegrationCallResult>(integrationCallResult);
        return result;
    }

    private IntegrationCallResult addSteeringPool(SteeringPool steeringPool) {
        String callDescription = String.format("Adding steering pool for realm - %s", steeringPool.getRealmId());
        logger.info(callDescription);

        WSConfigElement element = steeringPool.toWSConfigElement();
        try {
            if (logger.isDebugEnabled()) {
                WSConfigElementUtility.printElement(element);
            }
            nncService.getConfigMgmtIF().addConfigElement("dev-LAB", element);
            // ((NNCTransaction) (((DefaultTransactionStatus) TransactionInterceptor.currentTransactionStatus())
            // .getTransaction())).getOperations().add(new AbstractBaseOperationImpl(this, steeringPool));
        } catch (AcmeConfigWSFault e) {
            throw new RuntimeException("Could not add element " + steeringPool.getId() + "\n" + element.toString(), e);
        } catch (AcmeAdminWSFault e) {
            throw new RuntimeException("Could not add element " + steeringPool.getId() + "\n" + element.toString(), e);
        } catch (Throwable e) {
            throw new RuntimeException("Could not add element " + steeringPool.getId() + "\n" + element.toString(), e);
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        IntegrationCallResult integrationCallResult = new IntegrationCallResult(callDescription);
        return integrationCallResult;
    }
}
