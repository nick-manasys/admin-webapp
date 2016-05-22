package nz.co.example.dev.integration.calls;

import java.util.concurrent.Future;

import nz.co.example.dev.domain.model.NetworkInterface;
import nz.co.example.dev.integration.IntegrationCallResult;
import nz.co.example.dev.integration.NNCService;
import nz.co.example.dev.integration.operation.AbstractBaseOperationImpl;
import nz.co.example.dev.integration.tx.NNCTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.support.DefaultTransactionStatus;

import com.acmepacket.ems.ws.service.fault.AcmeAdminWSFault;
import com.acmepacket.ems.ws.service.fault.AcmeConfigWSFault;
import com.acmepacket.ems.ws.service.userobjects.WSConfigElement;

/**
 * Provides the service call wrapped in a future
 * required to add a network interface the
 * dev management server.
 * 
 * @author nivanov
 * 
 */
// @Transactional(propagation = Propagation.REQUIRED)
@Service
public class AddNetworkInterface {

    public static final String ADD_NETWORK_INTERFACE = "addNetworkInterface";

    private static final Logger logger = LoggerFactory.getLogger(AddNetworkInterface.class);

    @Autowired
    @Qualifier("canned")
    private NNCService nncService;

    // @Async
    public Future<IntegrationCallResult> call(NetworkInterface networkInterface) {
        IntegrationCallResult integrationCallResult = addNetworkInterface(networkInterface);
        AsyncResult<IntegrationCallResult> result = new AsyncResult<IntegrationCallResult>(integrationCallResult);
        return result;
    }

    private IntegrationCallResult addNetworkInterface(NetworkInterface networkInterface) {
        String callDescription = String.format("Adding network interface - %s", networkInterface.getId());
        logger.info(callDescription);

        WSConfigElement element = networkInterface.toWSConfigElement();
        try {
            if (logger.isDebugEnabled()) {
                WSConfigElementUtility.printElement(element);
            }
            nncService.getConfigMgmtIF().addConfigElement("dev-LAB", element);
            // ((NNCTransaction) (((DefaultTransactionStatus) TransactionInterceptor.currentTransactionStatus())
            // .getTransaction())).getOperations().add(new AbstractBaseOperationImpl(this, networkInterface));
        } catch (AcmeConfigWSFault e) {
            throw new RuntimeException("Could not add element " + networkInterface.getId() + "\n" + element.toString(),
                    e);
        } catch (AcmeAdminWSFault e) {
            throw new RuntimeException("Could not add element " + networkInterface.getId() + "\n" + element.toString(),
                    e);
        } catch (Throwable e) {
            throw new RuntimeException("Could not add element " + networkInterface.getId() + "\n" + element.toString(),
                    e);
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
