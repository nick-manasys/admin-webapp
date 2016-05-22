/**
 * 
 */
package test.nz.co.example.dev.mvc;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import nz.co.example.dev.domain.logic.CircuitConfigData;
import nz.co.example.dev.domain.logic.SipDomainLogic;
import nz.co.example.dev.domain.model.BaseCircuit;
import nz.co.example.dev.mvc.CircuitForm;
import nz.co.example.dev.mvc.RelinquishCircuitController;
import nz.co.example.dev.mvc.ViewName;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author nivanov
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class RelinquishCircuitControllerTest {
    
    @Mock
    private SipDomainLogic sipDomainLogic;
    
    @Mock
    private CircuitConfigData circuitConfigData;
        
    @InjectMocks
    private RelinquishCircuitController controller;
    
    @Mock
    private BaseCircuit circuit;
    
    @Mock
    private CircuitForm circuitForm;
    
    @Mock
    private BindingResult result;
    
    @Test
    public void relinquishShouldRelinquishTheCircuitAndRedirectToList() {
        //Given
        given(sipDomainLogic.createCircuit(circuitForm)).willReturn(circuit);
        //When
        ModelAndView modelAndView = controller.relinquish(circuitForm, result);
        
        //Then
        assertThat(modelAndView.getViewName()).isEqualTo(ViewName.REDIRECT_LIST_CIRCUITS.toString());
        Mockito.verify(sipDomainLogic).removeCircuit(circuit);
    }
}
