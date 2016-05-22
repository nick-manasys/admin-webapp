/**
 * 
 */
package nz.co.example.dev.integration.calls;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Given a map of futures representing asynchronous calls, tests whether they are all completed.
 * 
 * @author nivanov
 *
 */
@Component
public class FuturesCompletionTester {

    private static final Logger logger = LoggerFactory.getLogger(FuturesCompletionTester.class);

    public boolean allFuturesDone(Map<String, Future<?>> allFutures, long sleepIfNotCompleteInMillis) {
        boolean allFuturesDone = true;
        for (Entry<String, Future<?>> future : allFutures.entrySet()) {
            if (!future.getValue().isDone())
            {
                logger.debug(String.format("Still waiting for %s", future.getKey()));
                allFuturesDone = false;
            }
        }
        if (!allFuturesDone)
        {
            logger.debug(String.format("Not all returned - will try again in %s milliseconds.", sleepIfNotCompleteInMillis));
            try {
                Thread.sleep(sleepIfNotCompleteInMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            logger.debug("All returned.");            
        }
        
        return allFuturesDone;
    }
   
    
}
