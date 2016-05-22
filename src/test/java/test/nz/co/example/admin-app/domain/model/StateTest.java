package test.nz.co.example.dev.domain.model;

import static nz.co.example.dev.domain.model.State.DISABLED;
import static nz.co.example.dev.domain.model.State.ENABLED;
import static org.fest.assertions.Assertions.assertThat;
import nz.co.example.dev.domain.model.State;

import org.junit.Test;

public class StateTest {

    @Test
    public void shouldCreateCorrectToStringWhenDisabled() {
        State state = DISABLED;
        assertThat(state.toString()).isEqualTo("state disabled");
    }

    @Test
    public void shouldCreateCorrectToStringWhenEnabled() {
        State state = ENABLED;
        assertThat(state.toString()).isEqualTo("state enabled");
    }
}
