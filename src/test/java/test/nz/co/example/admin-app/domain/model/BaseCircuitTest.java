package test.nz.co.example.dev.domain.model;

import java.net.InetAddress;
import java.net.UnknownHostException;

import junit.framework.Assert;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import nz.co.example.dev.domain.model.BaseCircuit;

import org.junit.Test;

public class BaseCircuitTest {

    @Test
    public void shouldVerifyEqualsAndHashCode() throws UnknownHostException {
        EqualsVerifier
                .forClass(BaseCircuit.class)
                .suppress(Warning.NULL_FIELDS, Warning.NONFINAL_FIELDS)
                .withPrefabValues(InetAddress.class, InetAddress.getByName("1.1.1.1"), InetAddress.getByName("1.1.1.2"))
                .verify();
    }

    @Test
    public void testGetKey() {
        String region = "auc";
        String carrierId = "TEST";
        String trunkNumber = "01";
        String vLAN = "2251";

        String key = region + "-" + vLAN;

        BaseCircuit circuit = new BaseCircuit();
        circuit.setVLAN(vLAN);
        circuit.setCarrierId(carrierId);
        circuit.setTrunkNumber(trunkNumber);
        circuit.setRegion(region);

        Assert.assertTrue(key.equals(circuit.getKey()));
    }
}
