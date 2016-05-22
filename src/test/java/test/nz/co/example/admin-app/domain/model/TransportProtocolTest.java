package test.nz.co.example.dev.domain.model;

import static nz.co.example.dev.domain.model.TransportProtocol.TCP;
import static nz.co.example.dev.domain.model.TransportProtocol.UDP;
import static org.fest.assertions.Assertions.assertThat;
import nz.co.example.dev.domain.model.TransportProtocol;

import org.junit.Test;

public class TransportProtocolTest {

    @Test
    public void shouldCreateCorrectToStringWhenTCP() {
        TransportProtocol transportProtocol = TCP;
        assertThat(transportProtocol.toString()).isEqualTo("transport-protocol TCP");
    }

    @Test
    public void shouldCreateCorrectToStringWhenUDP() {
        TransportProtocol transportProtocol = UDP;
        assertThat(transportProtocol.toString()).isEqualTo("transport-protocol UDP");
    }

}
