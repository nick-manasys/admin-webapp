package nz.co.example.dev.integration.calls;

import java.util.concurrent.Future;

import nz.co.example.dev.domain.model.SipInterface;
import nz.co.example.dev.integration.IntegrationCallResult;
import nz.co.example.dev.integration.NNCService;
import nz.co.example.dev.integration.operation.AbstractBaseOperationImpl;
import nz.co.example.dev.integration.tx.NNCTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.support.DefaultTransactionStatus;

import com.acmepacket.ems.ws.service.fault.AcmeAdminWSFault;
import com.acmepacket.ems.ws.service.fault.AcmeConfigWSFault;
import com.acmepacket.ems.ws.service.userobjects.WSConfigElement;

/**
 * Provides the service call wrapped in a future
 * required to add a sip interface the
 * dev management server.
 * 
 * @author nivanov
 * 
 */
// @Transactional(propagation = Propagation.REQUIRED)
@Service
public class AddSipInterface {
    @Autowired
    private NNCService nncService;

    public static final String ADD_SECONDARY_SIP_INTERFACE = "addSecondarySipInterface";

    public static final String ADD_PRIMARY_SIP_INTERFACE = "addPrimarySipInterface";

    private static final Logger logger = LoggerFactory.getLogger(AddSipInterface.class);

    // @Async
    public Future<IntegrationCallResult> call(SipInterface sipInterface) {
        IntegrationCallResult integrationCallResult = addSipInterface(sipInterface);
        AsyncResult<IntegrationCallResult> result = new AsyncResult<IntegrationCallResult>(integrationCallResult);
        return result;
    }

    private IntegrationCallResult addSipInterface(SipInterface sipInterface) {
        String callDescription = String.format("Adding sip interface for realm - %s", sipInterface.getRealmId());
        logger.info(callDescription);

        WSConfigElement element = sipInterface.toWSConfigElement();
        try {
            if (logger.isDebugEnabled()) {
                WSConfigElementUtility.printElement(element);
            }
            nncService.getConfigMgmtIF().addConfigElement("dev-LAB", element);
            // ((NNCTransaction) (((DefaultTransactionStatus) TransactionInterceptor.currentTransactionStatus())
            // .getTransaction())).getOperations().add(new AbstractBaseOperationImpl(this, sipInterface));
        } catch (AcmeConfigWSFault e) {
            throw new RuntimeException("Could not add element " + sipInterface.getId() + "\n" + element.toString(), e);
        } catch (AcmeAdminWSFault e) {
            throw new RuntimeException("Could not add element " + sipInterface.getId() + "\n" + element.toString(), e);
        } catch (Throwable e) {
            throw new RuntimeException("Could not add element " + sipInterface.getId() + "\n" + element.toString(), e);
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
