package it.nz.co.example.dev.integration.calls;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import nz.co.example.dev.domain.model.NetworkInterface;
import nz.co.example.dev.integration.IntegrationCallResult;
import nz.co.example.dev.integration.calls.AddNetworkInterface;
import nz.co.example.dev.integration.calls.RemoveNetworkInterface;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import test.nz.co.example.dev.testsupport.Fixtures;

// @RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml" })
@RunWith(MockitoJUnitRunner.class)
public class AddNetworkInterfaceTest {
    @Mock
    private AddNetworkInterface addNetworkInterface;

    @Mock
    private RemoveNetworkInterface removeNetworkInterface;

    @Before
    public void setUp() throws Exception {
        // EMPTY
    }

    @After
    public void tearDown() throws Exception {
        // EMPTY
    }

    @Test
    public void testNothing() {
        // SUCCESS
    }

    @Test
    public void testCall() throws Exception {
        IntegrationCallResult addNetworkInterfaceResult = null;
        IntegrationCallResult removeNetworkInterfaceResult = null;
        //
        when(addNetworkInterface.call(Matchers.any(NetworkInterface.class))).thenReturn(
                new Future<IntegrationCallResult>() {

                    @Override
                    public boolean cancel(boolean mayInterruptIfRunning) {
                        // TODO Auto-generated method stub
                        return false;
                    }

                    @Override
                    public boolean isCancelled() {
                        // TODO Auto-generated method stub
                        return false;
                    }

                    @Override
                    public boolean isDone() {
                        // TODO Auto-generated method stub
                        return false;
                    }

                    @Override
                    public IntegrationCallResult get() throws InterruptedException, ExecutionException {
                        return new IntegrationCallResult("") {

                        };
                    }

                    @Override
                    public IntegrationCallResult get(long timeout, TimeUnit unit) throws InterruptedException,
                            ExecutionException, TimeoutException {
                        // TODO Auto-generated method stub
                        return null;
                    }

                });

        // save network interface
        NetworkInterface networkInterface = Fixtures.createNetworkInterfaceWithoutGateway();
        Future<IntegrationCallResult> callResult = addNetworkInterface.call(networkInterface);
        addNetworkInterfaceResult = callResult.get();
        assertTrue(!addNetworkInterfaceResult.hasErrors() && !addNetworkInterfaceResult.hasWarnings());

        try {
            // remove network interface
            Future<IntegrationCallResult> callResult2 = removeNetworkInterface.call(networkInterface);
            removeNetworkInterfaceResult = callResult2.get();
            assertTrue(!removeNetworkInterfaceResult.hasErrors() && !removeNetworkInterfaceResult.hasWarnings());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
