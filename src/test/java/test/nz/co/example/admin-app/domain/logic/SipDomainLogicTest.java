package test.nz.co.example.dev.domain.logic;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.ArrayList;
import java.util.List;

import nz.co.example.dev.domain.exception.UnknownCircuitTypeException;
import nz.co.example.dev.domain.logic.CircuitCache;
import nz.co.example.dev.domain.logic.CircuitConfigData;
import nz.co.example.dev.domain.logic.CircuitFactory;
import nz.co.example.dev.domain.logic.CircuitFormFactory;
import nz.co.example.dev.domain.logic.CircuitSubComponentSorter;
import nz.co.example.dev.domain.logic.CircuitSubComponents;
import nz.co.example.dev.domain.logic.SipDomainLogicImpl;
import nz.co.example.dev.domain.logic.SortedCircuitResult;
import nz.co.example.dev.domain.model.Circuit;
import nz.co.example.dev.domain.model.WSipLayerTwoCircuit;
import nz.co.example.dev.integration.SessionBorderControllerServices;
import nz.co.example.dev.mvc.CircuitForm;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import test.nz.co.example.dev.testsupport.Fixtures;

@RunWith(MockitoJUnitRunner.class)
public class SipDomainLogicTest {

    @Mock
    private CircuitFormFactory<Circuit> circuitFormFactory;

    @Mock
    private CircuitFactory<Circuit> circuitFactory;

    @Mock
    private CircuitCache circuitCache;

    @Mock
    private SessionBorderControllerServices sessionBorderControllerServices;

    @Mock
    private CircuitSubComponentSorter circuitSubComponentSorter;

    @Mock
    private CircuitSubComponents circuitSubComponents;

    @Mock
    private SortedCircuitResult<Circuit> matchedSubComponents;

    @Mock
    private List<Circuit> circuitList;

    @Mock
    private Circuit circuit;

    @Mock
    private Circuit oldCircuit;

    @InjectMocks
    private SipDomainLogicImpl sipDomainLogicImpl;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        List<CircuitFormFactory<Circuit>> circuitFormFactories = new ArrayList<CircuitFormFactory<Circuit>>();
        circuitFormFactories.add(circuitFormFactory);
        sipDomainLogicImpl.setCircuitFormFactories(circuitFormFactories);

        List<CircuitFactory<Circuit>> circuitFactories = new ArrayList<CircuitFactory<Circuit>>();
        circuitFactories.add(circuitFactory);
        sipDomainLogicImpl.setCircuitFactories(circuitFactories);
    }

    @Test
    public void getAllCircuitsGivenEmptyCacheShouldCallTheIntegrationServices() {
        // Given
        given(circuitCache.isEmpty()).willReturn(true);
        given(sessionBorderControllerServices.getAllCircuitSubComponents()).willReturn(circuitSubComponents);
        given(circuitSubComponentSorter.getSortedCircuits(circuitSubComponents)).willReturn(matchedSubComponents);
        given(matchedSubComponents.getSortedCircuits()).willReturn(circuitList);

        // When
        List<Circuit> allCircuits = sipDomainLogicImpl.getAllCircuits();

        // Then
        assertThat(allCircuits.size()).isEqualTo(circuitList.size());
        verify(circuitCache).addCircuits(circuitList);

    }

    @Test
    public void addCircuitShouldAddViaServicesAndAddToCache() {
        // When
        sipDomainLogicImpl.addCircuit(circuit);

        // Then
        verify(sessionBorderControllerServices).addNewCircuit(circuit);
        verify(circuitCache).addCircuit(circuit);
    }

    // This is really a placeholder test for when the real method implementation is added.
    @Test
    public void removeCircuitShouldRemoveViaServicesAndRemoveFromCache() {
        // When
        sipDomainLogicImpl.removeCircuit(circuit);

        // Then
        verify(sessionBorderControllerServices).relinquishCircuit(circuit);
        verify(circuitCache).removeCircuit(circuit);

    }

    // This is really a placeholder test for when the real method implementation is added.
    @Test
    public void modifyCircuitShouldUpdateViaServicesAndUpdateCache() {
        // When
        sipDomainLogicImpl.modifyCircuit(oldCircuit, circuit);

        // Then
        verify(sessionBorderControllerServices).modifyCircuit(oldCircuit, circuit);
        verify(circuitCache).modifyCircuit(oldCircuit, circuit);

    }

    @Test
    public void getAllCircuitsGivenNonEmptyCacheShouldNotCallTheIntegrationServices() {
        // Given
        given(circuitCache.isEmpty()).willReturn(false);
        given(circuitCache.getCircuits()).willReturn(circuitList);

        // When
        List<Circuit> allCircuits = sipDomainLogicImpl.getAllCircuits();

        // Then
        assertThat(allCircuits).isEqualTo(circuitList);
        verifyZeroInteractions(sessionBorderControllerServices);
        verifyZeroInteractions(circuitSubComponentSorter);
        verifyZeroInteractions(matchedSubComponents);

    }

    @Test
    public void createCurcuitFormWhenFactoryIsFoundShouldCreateForm() {
        // Given
        WSipLayerTwoCircuit circuit = Fixtures.createWSipLayerTwoCircuit();
        given(circuitFormFactory.createsFromType(circuit)).willReturn(true);
        given(circuitFormFactory.create(Mockito.any(Circuit.class), Mockito.any(CircuitConfigData.class))).willReturn(
                Fixtures.createCircuitForm());
        given(circuitCache.getCircuit(circuit.getKey())).willReturn(circuit);

        // when
        CircuitForm circuitForm = sipDomainLogicImpl.createCircuitForm(circuit);

        // then
        // Here we are merely testing that the correct form factory was found, the form object population is handled in
        // other tests.
        assertThat(circuitForm).isNotNull();
    }

    @Test
    public void createCurcuitFormWhenFactoryIsNotFoundShouldThrowException() {
        // Given
        WSipLayerTwoCircuit circuit = Fixtures.createWSipLayerTwoCircuit();
        given(circuitFormFactory.createsFromType(circuit)).willReturn(false);
        thrown.expect(UnknownCircuitTypeException.class);
        thrown.expectMessage("We do not have a form factory able to handle circuits of type - W_SIP_LAYER_TWO. This realy shouldn't ever happen and represents a coding error.");

        // when
        sipDomainLogicImpl.createCircuitForm(circuit);

        // then
        // We should have had an exception thrown.

    }

    @Test
    public void createCurcuitWhenFactoryIsFoundShouldCreateCircuit() {
        // Given
        WSipLayerTwoCircuit circuit = Fixtures.createWSipLayerTwoCircuit();
        CircuitForm circuitForm = Fixtures.createCircuitForm(circuit);
        given(circuitFactory.createsType(circuitForm)).willReturn(true);
        given(circuitFactory.create(Mockito.any(CircuitForm.class), Mockito.any(CircuitConfigData.class))).willReturn(
                circuit);
        given(circuitCache.getCircuit(circuit.getKey())).willReturn(circuit);

        // when
        Circuit circuitReturned = sipDomainLogicImpl.createCircuit(circuitForm);

        // then
        // Here we are merely testing that the correct form factory was found, the form object population is handled in
        // other tests.
        assertThat(circuitReturned).isNotNull();
    }

    @Test
    public void createCurcuitWhenFactoryIsNotFoundShouldThrowException() {
        // Given
        WSipLayerTwoCircuit circuit = Fixtures.createWSipLayerTwoCircuit();
        CircuitForm circuitForm = Fixtures.createCircuitForm(circuit);
        given(circuitFactory.createsType(circuitForm)).willReturn(false);
        thrown.expect(UnknownCircuitTypeException.class);
        thrown.expectMessage("We do not have a factory able to handle circuits of type - W_SIP_LAYER_TWO. This realy shouldn't ever happen and represents a coding error.");

        // when
        sipDomainLogicImpl.createCircuit(circuitForm);

        // then
        // We should have had an exception thrown.
    }

}
