package test.nz.co.example.dev.domain.model;

import static nl.jqno.equalsverifier.Warning.NONFINAL_FIELDS;
import static nl.jqno.equalsverifier.Warning.NULL_FIELDS;
import static org.fest.assertions.Assertions.assertThat;

import java.net.InetAddress;
import java.net.UnknownHostException;

import nl.jqno.equalsverifier.EqualsVerifier;
import nz.co.example.dev.domain.model.SipPort;

import org.junit.Test;

import test.nz.co.example.dev.testsupport.Fixtures;
import static test.nz.co.example.dev.testsupport.Fixtures.*;

public class SipPortTest {

    @Test
    public void shouldCreateCorrectToStringForPrimaryInterfaceUdpSipPort() throws UnknownHostException {
        // Given
        SipPort sipPort = Fixtures.createPrimaryUdpSipPort();

        // When
        String sipPortToString = sipPort.toString();

        // Then
        assertThat(sipPortToString).isEqualTo(
                String.format("sip-port%naddress %s%nport %s%ntransport-protocol UDP%nallow-anonymous registered%n",
                        IP_ADDRESS, PRIMARY_SIP_PORT));
    }

    @Test
    public void shouldCreateCorrectToStringForPrimaryInterfaceTcpSipPort() throws UnknownHostException {
        // Given
        SipPort sipPort = Fixtures.createPrimaryTcpSipPort();
        // When
        String sipPortToString = sipPort.toString();

        // Then
        assertThat(sipPortToString).isEqualTo(
                String.format("sip-port%naddress %s%nport %s%ntransport-protocol TCP%nallow-anonymous registered%n",
                        SIP_TCP_IP_ADDRESS, PRIMARY_SIP_PORT));
    }

    @Test
    public void shouldCreateCorrectToStringForSecondaryInterfaceUdpSipPort() throws UnknownHostException {
        SipPort sipPort = Fixtures.createSecondaryUdpSipPort();
        assertThat(sipPort.toString()).isEqualTo(
                String.format("sip-port%naddress %s%nport %s%ntransport-protocol UDP%nallow-anonymous registered%n",
                        Fixtures.IP_ADDRESS, Fixtures.SECONDARY_SIP_PORT));
    }

    @Test
    public void shouldVerifyEqualsAndHashCode() throws UnknownHostException {
        EqualsVerifier
                .forClass(SipPort.class)
                .suppress(NULL_FIELDS, NONFINAL_FIELDS)
                .withPrefabValues(InetAddress.class, InetAddress.getByName("1.1.1.1"), InetAddress.getByName("1.1.1.2"))
                .verify();
    }

}
