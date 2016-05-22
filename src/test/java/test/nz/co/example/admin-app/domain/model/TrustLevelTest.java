package test.nz.co.example.dev.domain.model;

import static nz.co.example.dev.domain.model.TrustLevel.HIGH;
import static nz.co.example.dev.domain.model.TrustLevel.LOW;
import static nz.co.example.dev.domain.model.TrustLevel.MEDIUM;
import static nz.co.example.dev.domain.model.TrustLevel.NONE;
import static org.fest.assertions.Assertions.assertThat;
import nz.co.example.dev.domain.model.TrustLevel;

import org.junit.Test;

public class TrustLevelTest {

    @Test
    public void shouldCreateCorrectToStringWhenHigh() {
        TrustLevel trustLevel = HIGH;
        assertThat(trustLevel.toString()).isEqualTo("trust-level high");
    }

    @Test
    public void shouldCreateCorrectToStringWhenLow() {
        TrustLevel trustLevel = LOW;
        assertThat(trustLevel.toString()).isEqualTo("trust-level low");
    }

    @Test
    public void shouldCreateCorrectToStringWhenMedium() {
        TrustLevel trustLevel = MEDIUM;
        assertThat(trustLevel.toString()).isEqualTo("trust-level medium");
    }

    @Test
    public void shouldCreateCorrectToStringWhenNone() {
        TrustLevel trustLevel = NONE;
        assertThat(trustLevel.toString()).isEqualTo("trust-level none");
    }

}
