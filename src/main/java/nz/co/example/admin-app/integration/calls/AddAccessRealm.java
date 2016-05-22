package nz.co.example.dev.integration.calls;

import java.util.concurrent.Future;

import nz.co.example.dev.domain.model.AccessRealm;
import nz.co.example.dev.integration.IntegrationCallResult;
import nz.co.example.dev.integration.NNCService;
import nz.co.example.dev.integration.operation.AbstractBaseOperationImpl;
import nz.co.example.dev.integration.tx.NNCTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.support.DefaultTransactionStatus;

import com.acmepacket.ems.ws.service.fault.AcmeAdminWSFault;
import com.acmepacket.ems.ws.service.fault.AcmeConfigWSFault;
import com.acmepacket.ems.ws.service.userobjects.WSConfigElement;

/**
 * Provides the service call wrapped in a future
 * required to add an access realm on the
 * dev management server.
 * 
 * @author nivanov
 * 
 */
// @Transactional(propagation = Propagation.REQUIRED)
@Service
public class AddAccessRealm {

    private static final Logger logger = LoggerFactory.getLogger(AddAccessRealm.class);

    public static final String ADD_PRIMARY_ACCESS_REALM = "addPrimaryAccessRealm";

    public static final String ADD_SECONDARY_ACCESS_REALM = "addSecondaryAccessRealm";

    @Autowired
    private NNCService nncService;

    // @Async
    public Future<IntegrationCallResult> call(AccessRealm accessRealm) {
        IntegrationCallResult integrationCallResult = addAccessRealm(accessRealm);
        AsyncResult<IntegrationCallResult> result = new AsyncResult<IntegrationCallResult>(integrationCallResult);
        return result;
    }

    private IntegrationCallResult addAccessRealm(AccessRealm accessRealm) {
        String callDescription = String.format("Adding access realm - %s for network interface - %s",
                accessRealm.getIdentifier(), accessRealm.getNetworkInterfaces());
        logger.info(callDescription);

        WSConfigElement element = accessRealm.toWSConfigElement();
        try {
            if (logger.isDebugEnabled()) {
                WSConfigElementUtility.printElement(element);
            }
            nncService.getConfigMgmtIF().addConfigElement("dev-LAB", element);
            // ((NNCTransaction) (((DefaultTransactionStatus) TransactionInterceptor.currentTransactionStatus())
            // .getTransaction())).getOperations().add(new AbstractBaseOperationImpl(this, accessRealm));
        } catch (AcmeConfigWSFault e) {
            throw new RuntimeException("Could not add element " + accessRealm.getId() + "\n" + element.toString(), e);
        } catch (AcmeAdminWSFault e) {
            throw new RuntimeException("Could not add element " + accessRealm.getId() + "\n" + element.toString(), e);
        } catch (Throwable e) {
            throw new RuntimeException("Could not add element " + accessRealm.getId() + "\n" + element.toString(), e);
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
