package nz.co.example.dev.integration.calls;

import static nz.co.example.dev.integration.calls.GetAllAccessRealms.ACCESS_REALMS;
import static nz.co.example.dev.integration.calls.GetAllNetworkInterfaces.NETWORK_INTERFACES;
import static nz.co.example.dev.integration.calls.GetAllSipInterfaces.SIP_INTERFACES;
import static nz.co.example.dev.integration.calls.GetAllSteeringPools.STEERING_POOLS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import nz.co.example.dev.domain.exception.IntegrationException;
import nz.co.example.dev.domain.logic.CircuitSubComponents;
import nz.co.example.dev.domain.model.AccessRealm;
import nz.co.example.dev.domain.model.NetworkInterface;
import nz.co.example.dev.domain.model.SipInterface;
import nz.co.example.dev.domain.model.SteeringPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Provides a wrapper for all of the service calls
 * required to retrieve all of the Circuit Sub Components from the
 * dev management server.
 * 
 * Calls each of the separate calls to retrieve the particular subcomponent types
 * asynchronously and then collates the results.
 * 
 * Because the separate calls are spring configured and have the @See {@link @Async} annotation on the methods, the
 * executor queue configuration and management is handled by spring.
 * 
 * @author nivanov
 */
// @Transactional(propagation = Propagation.REQUIRED)
@Service
public class GetAllCircuitSubComponents {

    public static final int SLEEP_BETWEEN_RETRY_IN_MILLIS = 200;
    
    private static final int MAX_RETRIES = 10;

    @Autowired
    private GetAllAccessRealms getAllAccessRealms;

    @Autowired
    private GetAllNetworkInterfaces getAllNetworkInterfaces;

    @Autowired
    private GetAllSipInterfaces getAllSipInterfaces;

    @Autowired
    private GetAllSteeringPools getAllSteeringPools;

    @Autowired
    private FuturesCompletionTester futuresCompletionTester;

    private static final Logger logger = LoggerFactory.getLogger(GetAllCircuitSubComponents.class);

    @SuppressWarnings("unchecked")
    public CircuitSubComponents call() {
        logger.debug("Retrieving All Circuit Subcomponents.");
        List<NetworkInterface> networkInterfaces = null;
        List<AccessRealm> accessRealms = null;
        List<SipInterface> sipInterfaces = null;
        List<SteeringPool> steeringPools = null;

        Map<String, Future<?>> allFutures = createMapOfFutures();

        int attemptNumber = 0;

        do {
            try {
                attemptNumber++;
                if (attemptNumber >= MAX_RETRIES) {
                    cancelAllFutures(allFutures);
                }

                if (networkInterfaces == null && allFutures.get(NETWORK_INTERFACES).isDone()) {
                    networkInterfaces = (List<NetworkInterface>) allFutures.get(NETWORK_INTERFACES).get();
                }
                if (accessRealms == null && allFutures.get(ACCESS_REALMS).isDone()) {
                    accessRealms = (List<AccessRealm>) allFutures.get(ACCESS_REALMS).get();
                }
                if (sipInterfaces == null && allFutures.get(SIP_INTERFACES).isDone()) {
                    sipInterfaces = (List<SipInterface>) allFutures.get(SIP_INTERFACES).get();
                }
                if (steeringPools == null && allFutures.get(STEERING_POOLS).isDone()) {
                    steeringPools = (List<SteeringPool>) allFutures.get(STEERING_POOLS).get();
                }
            } catch (InterruptedException e) {
                logger.error("Error attempting to retrieve Circuit Subcomponents", e);
            } catch (ExecutionException e) {
                logger.error("Error attempting to retrieve Circuit Subcomponents", e);
            }
        } while (!futuresCompletionTester.allFuturesDone(allFutures, SLEEP_BETWEEN_RETRY_IN_MILLIS));

        CircuitSubComponents circuitSubComponents = new CircuitSubComponents(networkInterfaces, accessRealms,
                sipInterfaces, steeringPools);
        return circuitSubComponents;
    }

    private Map<String, Future<?>> createMapOfFutures() {
        Map<String, Future<?>> allFutures = new HashMap<String, Future<?>>();
        allFutures.put(NETWORK_INTERFACES, getAllNetworkInterfaces.call());
        allFutures.put(ACCESS_REALMS, getAllAccessRealms.call());
        allFutures.put(SIP_INTERFACES, getAllSipInterfaces.call());
        allFutures.put(STEERING_POOLS, getAllSteeringPools.call());
        return allFutures;
    }

    private void cancelAllFutures(Map<String, Future<?>> futures) {
        for (Future<?> future : futures.values()) {
            future.cancel(true);
        }
        throw new IntegrationException(String.format("Failed to retrieve all Circuit Subcomponents, retried %s times",
                MAX_RETRIES));
    }
}
