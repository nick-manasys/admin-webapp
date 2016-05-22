/**
 * 
 */
package test.nz.co.example.dev.mvc;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import nz.co.example.dev.integration.SessionBorderControllerServices;
import nz.co.example.dev.mvc.HomeController;
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
public class HomeControllerTest {
 
    @InjectMocks
    private HomeController controller;

    @Mock
    private SessionBorderControllerServices service;

    @Test
    public void homeShouldForwardToHomeScreen() {
        // Given
        // When
        try {
            when(service.getLoginBanner()).thenReturn("");
            ModelAndView modelAndView = controller.home();

            // Then
            assertThat(modelAndView.getViewName()).isEqualTo(ViewName.HOME.toString());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void enterApplicationShouldForwardToListScreen() {
        // Given
        // When
        String view = controller.enterApplication();

        // Then
        assertThat(view).isEqualTo(ViewName.REDIRECT_LIST_CIRCUITS.toString());

    }
}
