/**
 * 
 */
package it.nz.co.example.dev.integration.callables;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import nz.co.example.dev.domain.exception.IntegrationException;
import nz.co.example.dev.domain.logic.CircuitSubComponents;
import nz.co.example.dev.domain.model.AccessRealm;
import nz.co.example.dev.domain.model.NetworkInterface;
import nz.co.example.dev.domain.model.SipInterface;
import nz.co.example.dev.domain.model.SteeringPool;
import nz.co.example.dev.integration.calls.FuturesCompletionTester;
import nz.co.example.dev.integration.calls.GetAllAccessRealms;
import nz.co.example.dev.integration.calls.GetAllCircuitSubComponents;
import nz.co.example.dev.integration.calls.GetAllNetworkInterfaces;
import nz.co.example.dev.integration.calls.GetAllSipInterfaces;
import nz.co.example.dev.integration.calls.GetAllSteeringPools;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author nivanov
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class GetAllCircuitSubComponentsTest {

    @Mock
    private GetAllAccessRealms getAllAccessRealms;

    @Mock
    private GetAllNetworkInterfaces getAllNetworkInterfaces;

    @Mock
    private GetAllSipInterfaces getAllSipInterfaces;

    @Mock
    private GetAllSteeringPools getAllSteeringPools;

    @Mock
    private Future<List<NetworkInterface>> getAllNetworkInterfacesFuture;

    @Mock
    private Future<List<AccessRealm>> getAllAccessRealmsFuture;

    @Mock
    private Future<List<SipInterface>> getAllSipInterfacesFuture;

    @Mock
    private Future<List<SteeringPool>> getAllSteeringPoolsFuture;

    @Mock
    private FuturesCompletionTester futuresCompletionTester;

    @InjectMocks
    private GetAllCircuitSubComponents getAllCircuitSubComponents;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() throws InterruptedException, ExecutionException {
        // Given

        // All callables return there associated future.
        given(getAllNetworkInterfaces.call()).willReturn(getAllNetworkInterfacesFuture);
        given(getAllAccessRealms.call()).willReturn(getAllAccessRealmsFuture);
        given(getAllSipInterfaces.call()).willReturn(getAllSipInterfacesFuture);
        given(getAllSteeringPools.call()).willReturn(getAllSteeringPoolsFuture);

        // All futures eventually return empty list
        List<NetworkInterface> emptyNetworkInterfaceList = Collections.emptyList();
        given(getAllNetworkInterfacesFuture.get()).willReturn(emptyNetworkInterfaceList);
        List<AccessRealm> emptyAccessRealmList = Collections.emptyList();
        given(getAllAccessRealmsFuture.get()).willReturn(emptyAccessRealmList);
        List<SipInterface> emptySipInterfaceList = Collections.emptyList();
        given(getAllSipInterfacesFuture.get()).willReturn(emptySipInterfaceList);
        List<SteeringPool> emptySteeringPoolList = Collections.emptyList();
        given(getAllSteeringPoolsFuture.get()).willReturn(emptySteeringPoolList);
    }

    @Test
    public void getWhenAllFuturesReturnShouldReturnCircuitSubComponents() {
        // Given
        given(getAllNetworkInterfacesFuture.isDone()).willReturn(true);
        given(getAllAccessRealmsFuture.isDone()).willReturn(true);
        given(getAllSipInterfacesFuture.isDone()).willReturn(true);
        given(getAllSteeringPoolsFuture.isDone()).willReturn(true);
        given(futuresCompletionTester.allFuturesDone(Mockito.anyMap(), Mockito.anyInt())).willReturn(true);

        // When
        CircuitSubComponents circuitSubComponents = getAllCircuitSubComponents.call();

        // Then
        assertThat(circuitSubComponents).isNotNull();
    }

    @Test
    public void getWhenNetworkInterfaceFutureWaitsShouldReturnCircuitSubComponents() {
        // Given
        given(getAllNetworkInterfacesFuture.isDone()).willReturn(false, false, true);
        given(getAllAccessRealmsFuture.isDone()).willReturn(true);
        given(getAllSipInterfacesFuture.isDone()).willReturn(true);
        given(getAllSteeringPoolsFuture.isDone()).willReturn(true);
        given(futuresCompletionTester.allFuturesDone(Mockito.anyMap(), Mockito.anyInt()))
                .willReturn(false, false, true);

        // When
        CircuitSubComponents circuitSubComponents = getAllCircuitSubComponents.call();

        // Then
        assertThat(circuitSubComponents).isNotNull();
    }

    @Test
    public void getWhenAccessRealmsFutureWaitsShouldReturnCircuitSubComponents() {
        // Given
        given(getAllNetworkInterfacesFuture.isDone()).willReturn(true);
        given(getAllAccessRealmsFuture.isDone()).willReturn(false, false, true);
        given(getAllSipInterfacesFuture.isDone()).willReturn(true);
        given(getAllSteeringPoolsFuture.isDone()).willReturn(true);
        given(futuresCompletionTester.allFuturesDone(Mockito.anyMap(), Mockito.anyInt()))
                .willReturn(false, false, true);

        // When
        CircuitSubComponents circuitSubComponents = getAllCircuitSubComponents.call();

        // Then
        assertThat(circuitSubComponents).isNotNull();
    }

    @Test
    public void getWhenSipInterfacesFutureWaitsShouldReturnCircuitSubComponents() {
        // Given
        given(getAllNetworkInterfacesFuture.isDone()).willReturn(true);
        given(getAllAccessRealmsFuture.isDone()).willReturn(true);
        given(getAllSipInterfacesFuture.isDone()).willReturn(false, false, true);
        given(getAllSteeringPoolsFuture.isDone()).willReturn(true);
        given(futuresCompletionTester.allFuturesDone(Mockito.anyMap(), Mockito.anyInt()))
                .willReturn(false, false, true);

        // When
        CircuitSubComponents circuitSubComponents = getAllCircuitSubComponents.call();

        // Then
        assertThat(circuitSubComponents).isNotNull();
    }

    @Test
    public void getWhenSteeringPoolsFutureWaitsShouldReturnCircuitSubComponents() {
        // Given
        given(getAllNetworkInterfacesFuture.isDone()).willReturn(true);
        given(getAllAccessRealmsFuture.isDone()).willReturn(true);
        given(getAllSipInterfacesFuture.isDone()).willReturn(true);
        given(getAllSteeringPoolsFuture.isDone()).willReturn(false, false, true);
        given(futuresCompletionTester.allFuturesDone(Mockito.anyMap(), Mockito.anyInt()))
                .willReturn(false, false, true);

        // When
        CircuitSubComponents circuitSubComponents = getAllCircuitSubComponents.call();

        // Then
        assertThat(circuitSubComponents).isNotNull();
    }

    @Test
    public void getWhenManyFuturesWaitShouldReturnCircuitSubComponents() {
        // Given
        given(getAllNetworkInterfacesFuture.isDone()).willReturn(false, false, true);
        given(getAllAccessRealmsFuture.isDone()).willReturn(false, false, true);
        given(getAllSipInterfacesFuture.isDone()).willReturn(true);
        given(getAllSteeringPoolsFuture.isDone()).willReturn(false, false, true);
        given(futuresCompletionTester.allFuturesDone(Mockito.anyMap(), Mockito.anyInt()))
                .willReturn(false, false, true);

        // When
        CircuitSubComponents circuitSubComponents = getAllCircuitSubComponents.call();

        // Then
        assertThat(circuitSubComponents).isNotNull();
    }

    @Test
    public void getWhenMaxRetryExceededShouldThrowIntegrationException() {
        // Given
        given(getAllNetworkInterfacesFuture.isDone()).willReturn(false, false, false, false, false, false, false,
                false, false, false, false);
        given(getAllAccessRealmsFuture.isDone()).willReturn(false, false, false, false, true);
        given(getAllSipInterfacesFuture.isDone()).willReturn(true);
        given(getAllSteeringPoolsFuture.isDone()).willReturn(false, false, true);
        given(futuresCompletionTester.allFuturesDone(Mockito.anyMap(), Mockito.anyInt())).willReturn(false, false,
                false, false, false, false, false, false, false, false, false);

        thrown.expect(IntegrationException.class);
        thrown.expectMessage("Failed to retrieve all Circuit Subcomponents, retried 10 times");
        // When
        getAllCircuitSubComponents.call();

        // Then
        // Exception should have been thrown.

    }

}
