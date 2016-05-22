package test.nz.co.example.dev.domain.model;

import static org.fest.assertions.Assertions.assertThat;
import nz.co.example.dev.domain.model.GWHeartbeat;

import org.junit.Test;

public class GWHeartbeatTest {

    @Test
    public void shouldCreateCorrectToStringWhenDisabled() {
        assertThat(GWHeartbeat.createDisabled().toString()).isEqualTo(String.format("gw-heartbeat%nstate disabled"));
    }

    @Test
    public void shouldCreateCorrectToStringWhenEnsabled() {
        assertThat(GWHeartbeat.createEnabled().toString()).isEqualTo(String.format("gw-heartbeat%nstate enabled"));
    }

}
