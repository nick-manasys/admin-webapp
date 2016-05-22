package test.nz.co.example.dev.domain.model;

import static nz.co.example.dev.domain.model.TrustMode.AGENTS_ONLY;
import static nz.co.example.dev.domain.model.TrustMode.ALL;
import static nz.co.example.dev.domain.model.TrustMode.REALM_PREFIX;
import static nz.co.example.dev.domain.model.TrustMode.REGISTERED;
import static org.fest.assertions.Assertions.assertThat;
import nz.co.example.dev.domain.model.TrustMode;

import org.junit.Test;

public class TrustModeTest {

    @Test
    public void shouldCreateCorrectToStringWhenAgentsOnly() {
        TrustMode trustMode = AGENTS_ONLY;
        assertThat(trustMode.toString()).isEqualTo("trust-mode agents-only");
    }

    @Test
    public void shouldCreateCorrectToStringWhenAll() {
        TrustMode trustMode = ALL;
        assertThat(trustMode.toString()).isEqualTo("trust-mode all");
    }

    @Test
    public void shouldCreateCorrectToStringWhenRealmPrefix() {
        TrustMode trustMode = REALM_PREFIX;
        assertThat(trustMode.toString()).isEqualTo("trust-mode realm-prefix");
    }

    @Test
    public void shouldCreateCorrectToStringWhenRegistered() {
        TrustMode trustMode = REGISTERED;
        assertThat(trustMode.toString()).isEqualTo("trust-mode registered");
    }

}
