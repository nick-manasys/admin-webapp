package nz.co.example.dev.integration.calls;

import static nz.co.example.dev.integration.calls.RemoveAccessRealm.REMOVE_PRIMARY_ACCESS_REALM;
import static nz.co.example.dev.integration.calls.RemoveAccessRealm.REMOVE_SECONDARY_ACCESS_REALM;
import static nz.co.example.dev.integration.calls.RemoveNetworkInterface.REMOVE_NETWORK_INTERFACE;
import static nz.co.example.dev.integration.calls.RemovdevInterface.REMOVE_PRIMARY_SIP_INTERFACE;
import static nz.co.example.dev.integration.calls.RemovdevInterface.REMOVE_SECONDARY_SIP_INTERFACE;
import static nz.co.example.dev.integration.calls.RemoveSteeringPool.REMOVE_STEERING_POOL;

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
 * @author nivanov
 */
@Service
public class RemoveWSipLayerTwoCircuit {

    @Autowired
    private RemoveNetworkInterface removeNetworkInterface;

    @Autowired
    private RemoveAccessRealm removeAccessRealm;

    @Autowired
    private RemovdevInterface removdevInterface;

    @Autowired
    private RemoveSteeringPool removeSteeringPool;

    @Autowired
    private FuturesCompletionTester futuresCompletionTester;

    private static final Logger logger = LoggerFactory.getLogger(RemoveWSipLayerTwoCircuit.class);

    private static final long SLEEP_BETWEEN_RETRY_IN_MILLIS = 500;

    private static final int MAX_RETRIES = 10;

    // @Async
    public IntegrationCallResult call(WSipLayerTwoCircuit circuit) {
        String description = String.format("Adding W-SIP Layer two circuit with ip address=%s and key=%s",
                circuit.getIpAddress(), circuit.getKey());
        IntegrationCallResult result = new IntegrationCallResult(description);

        Map<String, Future<?>> mapOfAddFutures = createMapOfAddFutures(circuit);
        IntegrationCallResult removeNetworkInterfaceResult = null;
        IntegrationCallResult removePrimaryAccessRealmResult = null;
        IntegrationCallResult removeSecondaryAccessRealmResult = null;
        IntegrationCallResult removePrimarySipInterfaceResult = null;
        IntegrationCallResult removeSecondarySipInterfaceResult = null;
        IntegrationCallResult removeSteeringPoolResult = null;

        int attemptNumber = 0;

        do {
            try {
                attemptNumber++;
                if (attemptNumber >= MAX_RETRIES) {
                    cancelAllFutures(mapOfAddFutures);
                }

                removePrimarySipInterfaceResult = checkForResult(mapOfAddFutures, removePrimarySipInterfaceResult,
                        REMOVE_PRIMARY_SIP_INTERFACE);
                removeSecondarySipInterfaceResult = checkForResult(mapOfAddFutures, removeSecondarySipInterfaceResult,
                        REMOVE_SECONDARY_SIP_INTERFACE);
                removeSteeringPoolResult = checkForResult(mapOfAddFutures, removeSteeringPoolResult,
                        REMOVE_STEERING_POOL);
                removePrimaryAccessRealmResult = checkForResult(mapOfAddFutures, removePrimaryAccessRealmResult,
                        REMOVE_PRIMARY_ACCESS_REALM);
                removeSecondaryAccessRealmResult = checkForResult(mapOfAddFutures, removeSecondaryAccessRealmResult,
                        REMOVE_SECONDARY_ACCESS_REALM);
                removeNetworkInterfaceResult = checkForResult(mapOfAddFutures, removeNetworkInterfaceResult,
                        REMOVE_NETWORK_INTERFACE);

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
        allFutures.put(REMOVE_PRIMARY_SIP_INTERFACE, removdevInterface.call(circuit.getPrimarySipInterface()));
        allFutures.put(REMOVE_SECONDARY_SIP_INTERFACE, removdevInterface.call(circuit.getSecondarySipInterface()));
        allFutures.put(REMOVE_STEERING_POOL, removeSteeringPool.call(circuit.getSteeringPool()));
        allFutures.put(REMOVE_PRIMARY_ACCESS_REALM, removeAccessRealm.call(circuit.getPrimaryAccessRealm()));
        allFutures.put(REMOVE_SECONDARY_ACCESS_REALM, removeAccessRealm.call(circuit.getSecondaryAccessRealm()));
        allFutures.put(REMOVE_NETWORK_INTERFACE, removeNetworkInterface.call(circuit.getNetworkInterface()));
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
