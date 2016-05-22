package test.nz.co.example.dev.domain.model;

import static nz.co.example.dev.domain.model.AnonymousMode.AGENTS_ONLY;
import static nz.co.example.dev.domain.model.AnonymousMode.ALL;
import static nz.co.example.dev.domain.model.AnonymousMode.REALM_PREFIX;
import static nz.co.example.dev.domain.model.AnonymousMode.REGISTERED;
import static nz.co.example.dev.domain.model.AnonymousMode.REGISTER_PREFIX;
import static org.fest.assertions.Assertions.assertThat;
import nz.co.example.dev.domain.model.AnonymousMode;

import org.junit.Test;

public class AnonymousModeTest {

    @Test
    public void shouldCreateCorrectToStringWhenAgentsOnly() {
        AnonymousMode mode = AGENTS_ONLY;
        assertThat(mode.toString()).isEqualTo("anonymous-mode agents-only");
    }

    @Test
    public void shouldCreateCorrectToStringWhenAll() {
        AnonymousMode mode = ALL;
        assertThat(mode.toString()).isEqualTo("anonymous-mode all");
    }

    @Test
    public void shouldCreateCorrectToStringWhenRealmPrefix() {
        AnonymousMode mode = REALM_PREFIX;
        assertThat(mode.toString()).isEqualTo("anonymous-mode realm-prefix");
    }

    @Test
    public void shouldCreateCorrectToStringWhenRegisteredPrefix() {
        AnonymousMode mode = REGISTER_PREFIX;
        assertThat(mode.toString()).isEqualTo("anonymous-mode register-prefix");
    }

    @Test
    public void shouldCreateCorrectToStringWhenRegistered() {
        AnonymousMode mode = REGISTERED;
        assertThat(mode.toString()).isEqualTo("anonymous-mode registered");
    }

}
