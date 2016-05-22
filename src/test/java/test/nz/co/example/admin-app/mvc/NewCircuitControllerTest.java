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
import nz.co.example.dev.mvc.CircuitForm;
import nz.co.example.dev.mvc.NewCircuitController;
import nz.co.example.dev.mvc.ViewName;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author nivanov
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class NewCircuitControllerTest {
    
    @Mock
    private SipDomainLogic sipDomainLogic;
    
    @Mock
    private Validator validator;

    @Mock
    private CircuitConfigData circuitConfigData;
    
    @Captor
    private ArgumentCaptor<CircuitForm> capturedCircuitForm;
    
    @InjectMocks
    private NewCircuitController controller;
    
    @Mock
    private BaseCircuit circuit;
    
    @Mock
    private CircuitForm circuitForm;
    
    @Mock
    private BindingResult result;
    
    @Test
    public void getShouldPopulateFormAndPassAsModel() {
        //Given
        //When
        ModelAndView modelAndView = controller.get();
        
        //Then
        assertThat(modelAndView.getViewName()).isEqualTo(ViewName.NEW_CIRCUIT.toString());
    }
   

    @Test
    public void formBackingObjectShouldPopulateFormAndPassAsModel() {
        //Given
        willDoNothing().given(circuitConfigData).populateSelectionOptions(capturedCircuitForm.capture());
        
        //When
        CircuitForm circuitForm = controller.formBackingObject();
        
        //Then
        verify(circuitConfigData).populateSelectionOptions(capturedCircuitForm.getValue());
        assertThat(circuitForm).isEqualTo(capturedCircuitForm.getValue());
    }
    
    @Test
    public void validateNewCircuitShouldReturnErrorsWhenValidationFails()
    {
        //Given
        willDoNothing().given(validator).validate(circuitForm, result);
        given(result.hasErrors()).willReturn(true);
        
        //When
        ModelAndView modelAndView = controller.validateNewCircuit(circuitForm, result);
        
        //Then
        verify(circuitForm, Mockito.never()).setValidatedReadyForSave(true);
        assertThat(modelAndView.getViewName()).isEqualTo(ViewName.NEW_CIRCUIT.toString());
        assertThat(modelAndView.getModel()).includes(entry("circuit", circuitForm));
        assertThat(modelAndView.getModel()).excludes(entry("fullCircuitDetails", circuit.toString()));
    }
    
    @Test
    public void validateNewCircuitShouldReturnValidatedFormWhenValidationPasses()
    {
        //Given
        willDoNothing().given(validator).validate(circuitForm, result);
        given(result.hasErrors()).willReturn(false);
        given(sipDomainLogic.createCircuit(circuitForm)).willReturn(circuit);
        
        //When
        ModelAndView modelAndView = controller.validateNewCircuit(circuitForm, result);
        
        //Then
        verify(circuitForm, Mockito.times(1)).setValidatedReadyForSave(true);
        assertThat(modelAndView.getViewName()).isEqualTo(ViewName.NEW_CIRCUIT.toString());
        assertThat(modelAndView.getModel()).includes(entry("circuit", circuitForm));
        assertThat(modelAndView.getModel()).includes(entry("fullCircuitDetails", circuit.toString()));
    }
    
    @Test
    public void saveNewCircuitShouldSaveThenRedirectToList()
    {
        //Given
        given(sipDomainLogic.createCircuit(circuitForm)).willReturn(circuit);
        
        //When
        String view = controller.saveNewCircuit(circuitForm, result);
        
        //then
        assertThat(view).isEqualTo(ViewName.REDIRECT_LIST_CIRCUITS.toString());
        verify(sipDomainLogic).addCircuit(circuit);
        
    }
    
    
    @Test
    public void cancelNewCircuitShouldRedirectToList()
    {
        
        //When
        String view = controller.cancelNewCircuit(circuitForm, result);
        
        //then
        assertThat(view).isEqualTo(ViewName.REDIRECT_LIST_CIRCUITS.toString());
        verifyZeroInteractions(sipDomainLogic);
        
    }
}
