package nz.co.example.dev.integration.calls;

import static nz.co.example.dev.integration.calls.AddAccessRealm.ADD_PRIMARY_ACCESS_REALM;
import static nz.co.example.dev.integration.calls.AddAccessRealm.ADD_SECONDARY_ACCESS_REALM;
import static nz.co.example.dev.integration.calls.AddNetworkInterface.ADD_NETWORK_INTERFACE;
import static nz.co.example.dev.integration.calls.AddSipInterface.ADD_PRIMARY_SIP_INTERFACE;
import static nz.co.example.dev.integration.calls.AddSipInterface.ADD_SECONDARY_SIP_INTERFACE;
import static nz.co.example.dev.integration.calls.AddSteeringPool.ADD_STEERING_POOL;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import nz.co.example.dev.domain.exception.IntegrationException;
import nz.co.example.dev.domain.model.WSipLayerTwoCircuit;
import nz.co.example.dev.integration.IntegrationCallResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Provides a wrapper for all of the service calls
 * required to add a new W-SIP Layer Two Circuit to the
 * dev management server.
 * 
 * Calls each of the separate calls to add the particular subcomponent types
 * asynchronously and then decides whether to roll back the changes.
 * 
 * Because the separate calls are spring configured and have the @See {@link @Async} annotation on the methods, the
 * executor queue configuration and management is handled by spring.
 * 
 * At the moment we are assuming that all of these subcomponents can be added asynchronously.
 * If this is not the case the calls should be easily changed to synchronous via the removal of the
 * annotation from the method.
 * 
 * 
 * TODO This is incomplete - it's needs to collate results and remove subcomponents in the event of a failure.
 * 
 * @author nivanov
 * 
 */
// @Transactional(propagation = Propagation.NESTED)
@Service
public class AddWSipLayerTwoCircuit {

    @Autowired
    private AddNetworkInterface addNetworkInterface;

    @Autowired
    private AddAccessRealm addAccessRealm;

    @Autowired
    private AddSipInterface addSipInterface;

    @Autowired
    private AddSteeringPool addSteeringPool;

    @Autowired
    private FuturesCompletionTester futuresCompletionTester;

    private static final Logger logger = LoggerFactory.getLogger(AddWSipLayerTwoCircuit.class);

    private static final long SLEEP_BETWEEN_RETRY_IN_MILLIS = 500;
    private static final int MAX_RETRIES = 10;

    public IntegrationCallResult call(WSipLayerTwoCircuit circuit) {
        String description = String.format("Adding W-SIP Layer two circuit with ip address=%s and key=%s",
                circuit.getIpAddress(), circuit.getKey());
        IntegrationCallResult result = new IntegrationCallResult(description);

        Map<String, Future<?>> mapOfAddFutures = createMapOfAddFutures(circuit);
        IntegrationCallResult addNetworkInterfaceResult = null;
        IntegrationCallResult addPrimaryAccessRealmResult = null;
        IntegrationCallResult addSecondaryAccessRealmResult = null;
        IntegrationCallResult addPrimarySipInterfaceResult = null;
        IntegrationCallResult addSecondarySipInterfaceResult = null;
        IntegrationCallResult addSteeringPoolResult = null;

        int attemptNumber = 0;

        do {
            try {
                attemptNumber++;
                if (attemptNumber >= MAX_RETRIES) {
                    cancelAllFutures(mapOfAddFutures);
                }

                addNetworkInterfaceResult = checkForResult(mapOfAddFutures, addNetworkInterfaceResult,
                        ADD_NETWORK_INTERFACE);
                addPrimaryAccessRealmResult = checkForResult(mapOfAddFutures, addPrimaryAccessRealmResult,
                        ADD_PRIMARY_ACCESS_REALM);
                addSecondaryAccessRealmResult = checkForResult(mapOfAddFutures, addSecondaryAccessRealmResult,
                        ADD_SECONDARY_ACCESS_REALM);
                addPrimarySipInterfaceResult = checkForResult(mapOfAddFutures, addPrimarySipInterfaceResult,
                        ADD_PRIMARY_SIP_INTERFACE);
                addSecondarySipInterfaceResult = checkForResult(mapOfAddFutures, addSecondarySipInterfaceResult,
                        ADD_SECONDARY_SIP_INTERFACE);
                addSteeringPoolResult = checkForResult(mapOfAddFutures, addSteeringPoolResult, ADD_STEERING_POOL);

            } catch (InterruptedException e) {
                logger.error("Error attempting to retrieve Circuit Subcomponents", e);
            } catch (ExecutionException e) {
                logger.error("Error attempting to retrieve Circuit Subcomponents", e);
            }
        } while (!futuresCompletionTester.allFuturesDone(mapOfAddFutures, SLEEP_BETWEEN_RETRY_IN_MILLIS));

        return result;
    }

    private IntegrationCallResult checkForResult(Map<String, Future<?>> mapOfAddFutures,
            IntegrationCallResult integrationCallResult, String resultKey) throws InterruptedException,
            ExecutionException {
        if (integrationCallResult == null && mapOfAddFutures.get(resultKey).isDone()) {
            integrationCallResult = (IntegrationCallResult) mapOfAddFutures.get(resultKey).get();
        }
        return integrationCallResult;
    }

    private Map<String, Future<?>> createMapOfAddFutures(WSipLayerTwoCircuit circuit) {
        Map<String, Future<?>> allFutures = new HashMap<String, Future<?>>();
        allFutures.put(ADD_NETWORK_INTERFACE, addNetworkInterface.call(circuit.getNetworkInterface()));
        allFutures.put(ADD_PRIMARY_ACCESS_REALM, addAccessRealm.call(circuit.getPrimaryAccessRealm()));
        allFutures.put(ADD_SECONDARY_ACCESS_REALM, addAccessRealm.call(circuit.getSecondaryAccessRealm()));
        allFutures.put(ADD_PRIMARY_SIP_INTERFACE, addSipInterface.call(circuit.getPrimarySipInterface()));
        allFutures.put(ADD_SECONDARY_SIP_INTERFACE, addSipInterface.call(circuit.getSecondarySipInterface()));
        allFutures.put(ADD_STEERING_POOL, addSteeringPool.call(circuit.getSteeringPool()));
        return allFutures;
    }

    private void cancelAllFutures(Map<String, Future<?>> futures) {
        for (Future<?> future : futures.values()) {
            future.cancel(true);
        }
        throw new IntegrationException(String.format("Failed to create all Circuit Subcomponents, retried %s times",
                MAX_RETRIES));
    }
}
