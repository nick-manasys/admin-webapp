package test.nz.co.example.dev.domain.model;

import static nz.co.example.dev.domain.model.ContactMode.LOOSE;
import static nz.co.example.dev.domain.model.ContactMode.MADDR;
import static nz.co.example.dev.domain.model.ContactMode.NONE;
import static nz.co.example.dev.domain.model.ContactMode.STRICT;
import static org.fest.assertions.Assertions.assertThat;
import nz.co.example.dev.domain.model.ContactMode;

import org.junit.Test;

public class ContactModeTest {

    @Test
    public void shouldCreateCorrectToStringWhenLoose() {
        ContactMode mode = LOOSE;
        assertThat(mode.toString()).isEqualTo("contact-mode loose");
    }

    @Test
    public void shouldCreateCorrectToStringWhenMAddr() {
        ContactMode mode = MADDR;
        assertThat(mode.toString()).isEqualTo("contact-mode maddr");
    }

    @Test
    public void shouldCreateCorrectToStringWhenNone() {
        ContactMode mode = NONE;
        assertThat(mode.toString()).isEqualTo("contact-mode none");
    }

    @Test
    public void shouldCreateCorrectToStringWhenStrict() {
        ContactMode mode = STRICT;
        assertThat(mode.toString()).isEqualTo("contact-mode strict");
    }

}
