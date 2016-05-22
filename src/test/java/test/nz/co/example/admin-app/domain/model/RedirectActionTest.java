package test.nz.co.example.dev.domain.model;

import static nz.co.example.dev.domain.model.RedirectAction.EMPTY;
import static nz.co.example.dev.domain.model.RedirectAction.PROXY;
import static nz.co.example.dev.domain.model.RedirectAction.RECORD_ROUTE;
import static nz.co.example.dev.domain.model.RedirectAction.REDIRECT;
import static nz.co.example.dev.domain.model.RedirectAction.STATELESS;
import static org.fest.assertions.Assertions.assertThat;
import nz.co.example.dev.domain.model.RedirectAction;

import org.junit.Test;

public class RedirectActionTest {

    @Test
    public void shouldCreateCorrectToStringWhenEmpty() {
        RedirectAction redirectAction = EMPTY;
        assertThat(redirectAction.toString()).isEqualTo("redirect-action empty");
    }

    @Test
    public void shouldCreateCorrectToStringWhenProxy() {
        RedirectAction redirectAction = PROXY;
        assertThat(redirectAction.toString()).isEqualTo("redirect-action proxy");
    }

    @Test
    public void shouldCreateCorrectToStringWhenRecordRoute() {
        RedirectAction redirectAction = RECORD_ROUTE;
        assertThat(redirectAction.toString()).isEqualTo("redirect-action record-route");
    }

    @Test
    public void shouldCreateCorrectToStringWhenRedirect() {
        RedirectAction redirectAction = REDIRECT;
        assertThat(redirectAction.toString()).isEqualTo("redirect-action redirect");
    }

    @Test
    public void shouldCreateCorrectToStringWhenStateless() {
        RedirectAction redirectAction = STATELESS;
        assertThat(redirectAction.toString()).isEqualTo("redirect-action stateless");
    }

}
