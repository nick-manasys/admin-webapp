/**
 * 
 */
package test.nz.co.example.dev.mvc;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;
import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nz.co.example.dev.domain.logic.SipDomainLogic;
import nz.co.example.dev.domain.model.Circuit;
import nz.co.example.dev.mvc.ListCircuitsController;
import nz.co.example.dev.mvc.ViewName;
import nz.co.example.dev.presentation.SessionKeys;

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
public class ListCircuitsControllerTest {
    @Mock
    private SipDomainLogic sipDomainLogic;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpSession session;

    @InjectMocks
    private ListCircuitsController controller;

    @Test
    public void getShouldPopulateCircuitListAndPassAsModel() {
        // Given
        List<Circuit> circuits = Collections.emptyList();
        given(sipDomainLogic.getAllCircuits()).willReturn(circuits);
        given(request.getSession(true)).willReturn(session);
        given(session.getAttribute(SessionKeys.PERSONID)).willReturn("88888888");

        // When
        ModelAndView modelAndView = controller.get(request, null);

        // Then
        assertThat(modelAndView.getViewName()).isEqualTo(ViewName.LIST_CIRCUITS.toString());
        assertThat(modelAndView.getModel()).includes(entry("circuits", circuits));
    }
}
