/**
 * 
 */
package test.nz.co.example.dev.mvc;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;
import nz.co.example.dev.mvc.ExceptionHandlerController;
import nz.co.example.dev.mvc.ViewName;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author nivanov
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ExceptionHandlerControllerTest {
        
    @InjectMocks
    private ExceptionHandlerController controller;
    
    @Mock
    private Exception exception;

    @Test
    public void handleExceptionShouldPopulateExceptionAsModel() {
        //Given
        //When
        ModelAndView modelAndView = controller.handleException(exception);
        
        //Then
        assertThat(modelAndView.getViewName()).isEqualTo(ViewName.ERROR.toString());
        assertThat(modelAndView.getModel()).includes(entry("ex", exception));
        
        
    }
}
