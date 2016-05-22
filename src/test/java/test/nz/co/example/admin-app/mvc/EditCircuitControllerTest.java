/**
 * 
 */
package test.nz.co.example.dev.mvc;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import nz.co.example.dev.domain.logic.CircuitConfigData;
import nz.co.example.dev.domain.logic.SipDomainLogic;
import nz.co.example.dev.domain.model.BaseCircuit;
import nz.co.example.dev.domain.model.Circuit;
import nz.co.example.dev.mvc.CircuitForm;
import nz.co.example.dev.mvc.EditCircuitController;
import nz.co.example.dev.mvc.SessionData;
import nz.co.example.dev.mvc.ViewName;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.ModelAndView;

import test.nz.co.example.dev.testsupport.Fixtures;

/**
 * @author nivanov
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class EditCircuitControllerTest {

    @Mock
    private SipDomainLogic sipDomainLogic;

    @Mock
    private Validator validator;

    @Mock
    private CircuitConfigData circuitConfigData;

    @Mock
    private BaseCircuit circuit;

    @Mock
    private BaseCircuit circuitPreMods;

    @Mock
    private BindingResult result;

    @Mock
    private SessionData sessionData;

    @InjectMocks
    private EditCircuitController controller;

    private CircuitForm circuitForm = Fixtures.createCircuitForm();

    private CircuitForm circuitFormPreMods = Fixtures.createCircuitForm();

    @Test
    public void getWhenCircuitRegionNotSelectedShouldRedirectToNewCircuit() {
        // Given
        given(circuit.getRegion()).willReturn("");

        // When
        ModelAndView modelAndView = controller.get(circuit, result);

        // Then
        assertThat(modelAndView.getViewName()).isEqualTo(ViewName.REDIRECT_NEW_CIRCUIT.toString());
    }

    @Test
    public void getWhenCircuitVLANNotSelectedShouldRedirectToNewCircuit() {
        // Given
        given(circuit.getRegion()).willReturn("auc");
        given(circuit.getVLAN()).willReturn("");

        // When
        ModelAndView modelAndView = controller.get(circuit, result);

        // Then
        assertThat(modelAndView.getViewName()).isEqualTo(ViewName.REDIRECT_NEW_CIRCUIT.toString());
    }

    @Test
    public void getWhenCircuitSelectedShouldPopulateFormAndPassAsModel() {
        // Given
        given(circuit.getRegion()).willReturn("auc");
        given(circuit.getVLAN()).willReturn("889");
        circuitForm = new CircuitForm();
        given(sipDomainLogic.createCircuitForm(Mockito.any(Circuit.class))).willReturn(circuitForm);
        given(sipDomainLogic.createCircuit(circuitForm)).willReturn(circuit);

        // When
        ModelAndView modelAndView = controller.get(circuit, result);

        // Then
        verify(circuitConfigData).populateSelectionOptions(circuitForm);
        assertThat(modelAndView.getViewName()).isEqualTo(ViewName.EDIT_CIRCUIT.toString());
        assertThat(modelAndView.getModel()).includes(entry("circuit", circuitForm));
        assertThat(modelAndView.getModel()).includes(entry("fullCircuitDetails", circuit.toString()));
    }

    @Test
    public void validateNewCircuitShouldReturnErrorsWhenValidationFails() {
        // Given
        circuitForm = Fixtures.createCircuitForm();
        willDoNothing().given(validator).validate(circuitForm, result);
        given(result.hasErrors()).willReturn(true);
        given(sessionData.getCircuitPreModifications()).willReturn(CircuitForm.newInstance(circuitForm));
        // When
        ModelAndView modelAndView = controller.validateCircuitMods(circuitForm, result);

        // Then
        assertThat(circuitForm.isValidatedReadyForSave()).isFalse();
        assertThat(modelAndView.getViewName()).isEqualTo(ViewName.EDIT_CIRCUIT.toString());
        assertThat(modelAndView.getModel()).includes(entry("circuit", circuitForm));
        assertThat(modelAndView.getModel()).excludes(entry("fullCircuitDetails", circuit.toString()));
    }

    @Test
    public void validateNewCircuitShouldReturnValidatedFormWhenValidationPasses() {
        // Given
        circuitForm = Fixtures.createCircuitForm();
        given(sessionData.getCircuitPreModifications()).willReturn(CircuitForm.newInstance(circuitForm));
        willDoNothing().given(validator).validate(circuitForm, result);
        given(result.hasErrors()).willReturn(false);
        given(sipDomainLogic.createCircuit(circuitForm)).willReturn(circuit);

        // When
        ModelAndView modelAndView = controller.validateCircuitMods(circuitForm, result);

        // Then
        assertThat(circuitForm.isValidatedReadyForSave()).isTrue();
        assertThat(modelAndView.getViewName()).isEqualTo(ViewName.EDIT_CIRCUIT.toString());
        assertThat(modelAndView.getModel()).includes(entry("circuit", circuitForm));
        assertThat(modelAndView.getModel()).includes(entry("fullCircuitDetails", circuit.toString()));
    }

    @Test
    public void saveCircuitModsShouldSaveThenRedirectToList() {
        // Given
        given(sipDomainLogic.createCircuit(circuitForm)).willReturn(circuit);
        given(sessionData.getCircuitPreModifications()).willReturn(circuitFormPreMods);
        given(sipDomainLogic.createCircuit(circuitFormPreMods)).willReturn(circuitPreMods);
        // When
        String view = controller.saveCircuitMods(circuitForm, result);

        // then
        assertThat(view).isEqualTo(ViewName.REDIRECT_LIST_CIRCUITS.toString());
        verify(sipDomainLogic).modifyCircuit(circuitPreMods, circuit);

    }

    @Test
    public void cancelCircuitModsShouldRedirectToList() {

        // When
        String view = controller.cancelCircuitMods(circuitForm, result);

        // then
        assertThat(view).isEqualTo(ViewName.REDIRECT_LIST_CIRCUITS.toString());
        verifyZeroInteractions(sipDomainLogic);

    }
}
