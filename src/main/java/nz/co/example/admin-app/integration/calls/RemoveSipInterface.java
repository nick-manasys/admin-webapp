package nz.co.example.dev.integration.calls;

import java.util.concurrent.Future;

import nz.co.example.dev.domain.model.SipInterface;
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
 * required to remove a sip interface the
 * dev management server.
 * 
 * @author nivanov
 * 
 */
@Service
public class RemovdevInterface {

    private static final Logger logger = LoggerFactory.getLogger(RemovdevInterface.class);

    public static final String REMOVE_PRIMARY_SIP_INTERFACE = "removePrimarySipInterface";

    public static final String REMOVE_SECONDARY_SIP_INTERFACE = "removeSecondarySipInterface";

    @Autowired
    private NNCService nncService;

    // @Async
    public Future<IntegrationCallResult> call(SipInterface sipInterface) {
        IntegrationCallResult integrationCallResult = removdevInterface(sipInterface);
        AsyncResult<IntegrationCallResult> result = new AsyncResult<IntegrationCallResult>(integrationCallResult);
        return result;
    }

    private IntegrationCallResult removdevInterface(SipInterface sipInterface) {
        String callDescription = String.format("Removing sip interface for realm - %s", sipInterface.getRealmId());
        logger.info(callDescription);

        WSConfigElement element = sipInterface.toWSConfigElement();
        try {
            if (logger.isDebugEnabled()) {
                WSConfigElementUtility.printElement(element);
            }
            nncService.getConfigMgmtIF().deleteConfigElement("dev-LAB", element);
        } catch (AcmeConfigWSFault e) {
            throw new RuntimeException("Could not remove element " + sipInterface.getId() + "\n" + element.toString(),
                    e);
        } catch (AcmeAdminWSFault e) {
            throw new RuntimeException("Could not remove element " + sipInterface.getId() + "\n" + element.toString(),
                    e);
        } catch (Throwable e) {
            throw new RuntimeException("Could not remove element " + sipInterface.getId() + "\n" + element.toString(),
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
