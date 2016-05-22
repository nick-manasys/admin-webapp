package test.nz.co.example.dev.domain.logic;

import static nz.co.example.dev.domain.model.CircuitType.UNKNOWN;
import static nz.co.example.dev.domain.model.CircuitType.W_SIP_LAYER_TWO;
import static org.fest.assertions.Assertions.assertThat;
import nz.co.example.dev.domain.logic.CircuitConfigData;
import nz.co.example.dev.domain.logic.WSipLayerTwoCircuitFormFactory;
import nz.co.example.dev.domain.logic.temp.HardCodedCircuitConfigData;
import nz.co.example.dev.domain.model.BaseCircuit;
import nz.co.example.dev.domain.model.WSipLayerTwoCircuit;
import nz.co.example.dev.mvc.CircuitForm;

import org.junit.Test;

import test.nz.co.example.dev.testsupport.Fixtures;

import static test.nz.co.example.dev.testsupport.Fixtures.*;

public class WSipLayerTwoCircuitFormFactoryTest {

    private WSipLayerTwoCircuitFormFactory circuitFormFactory = new WSipLayerTwoCircuitFormFactory();
    private WSipLayerTwoCircuit circuit;
    private CircuitConfigData circuitConfigData;

    @Test
    public void createsFromTypeShouldReturnTrueForWSipLayerTwo() {
        // Given
        BaseCircuit baseCircuit = new BaseCircuit("tux01", "218.101.10.1", "1", "Auckland", W_SIP_LAYER_TWO);
        // When
        boolean createsType = circuitFormFactory.createsFromType(baseCircuit);
        // Then
        assertThat(createsType).isTrue();
    }

    @Test
    public void createsFromTypeShouldReturnFalseForNonWSipLayerTwo() {
        // Given
        BaseCircuit baseCircuit = new BaseCircuit("tux01", "218.101.10.1", "1", "Auckland", UNKNOWN);
        // When
        boolean createsType = circuitFormFactory.createsFromType(baseCircuit);
        // Then
        assertThat(createsType).isFalse();
    }

    @Test
    public void createsFromTypeShouldReturnFalseForNullCircuitType() {
        // Given
        BaseCircuit baseCircuit = new BaseCircuit("tux01", "218.101.10.1", "1", "Auckland", null);
        // When
        boolean createsType = circuitFormFactory.createsFromType(baseCircuit);
        // Then
        assertThat(createsType).isFalse();
    }

    @Test
    public void createShouldCreateCircuitFormWithCorrectCarrierShortCode() {
        // Given
        createStandardCircuitAndConfigData();
        // When
        CircuitForm circuitForm = circuitFormFactory.create(circuit, circuitConfigData);

        // Then
        assertThat(circuitForm).isNotNull();
        assertThat(circuitForm.getCarrierShortCode()).isEqualTo(CARRIER_ID);
    }

    @Test
    public void createShouldCreateCircuitFormWithCorrectAccessVLan() {
        // Given
        createStandardCircuitAndConfigData();
        // When
        CircuitForm circuitForm = circuitFormFactory.create(circuit, circuitConfigData);

        // Then
        assertThat(circuitForm).isNotNull();
        assertThat(circuitForm.getAccessVLan()).isEqualTo(VLAN);
    }

    @Test
    public void createShouldCreateCircuitFormWithCorrectCarrierName() {
        // Given
        createStandardCircuitAndConfigData();
        // When
        CircuitForm circuitForm = circuitFormFactory.create(circuit, circuitConfigData);

        // Then
        assertThat(circuitForm).isNotNull();
        assertThat(circuitForm.getCarrierName()).isEqualTo(CARRIER_NAME);
    }

    @Test
    public void createShouldCreateCircuitFormWithCorrectCircuitType() {
        // Given
        createStandardCircuitAndConfigData();
        // When
        CircuitForm circuitForm = circuitFormFactory.create(circuit, circuitConfigData);

        // Then
        assertThat(circuitForm).isNotNull();
        assertThat(circuitForm.getCircuitType()).isEqualTo(W_SIP_LAYER_TWO.name());
    }

    @Test
    public void createShouldCreateCircuitFormWithCorrectDefaultGatewayIpAddress() {
        // Given
        createStandardCircuitAndConfigData();
        // When
        CircuitForm circuitForm = circuitFormFactory.create(circuit, circuitConfigData);

        // Then
        assertThat(circuitForm).isNotNull();
        assertThat(circuitForm.getDefaultGatewayIpAddress()).isEqualTo(GATEWAY);
    }

    @Test
    public void createShouldCreateCircuitFormWithNoDefaultGatewayIpAddress() {
        // Given
        createCircuitWithNoGatewayAndConfigData();
        // When
        CircuitForm circuitForm = circuitFormFactory.create(circuit, circuitConfigData);

        // Then
        assertThat(circuitForm).isNotNull();
        assertThat(circuitForm.getDefaultGatewayIpAddress()).isNullOrEmpty();
    }

    @Test
    public void createShouldCreateCircuitFormWithCorrectIpAddress() {
        // Given
        createStandardCircuitAndConfigData();
        // When
        CircuitForm circuitForm = circuitFormFactory.create(circuit, circuitConfigData);

        // Then
        assertThat(circuitForm).isNotNull();
        assertThat(circuitForm.getIpAddress()).isEqualTo(IP_ADDRESS);
    }

    @Test
    public void createShouldCreateCircuitFormWithCorrectNetworkMask() {
        // Given
        createStandardCircuitAndConfigData();
        // When
        CircuitForm circuitForm = circuitFormFactory.create(circuit, circuitConfigData);

        // Then
        assertThat(circuitForm).isNotNull();
        assertThat(circuitForm.getNetworkMask()).isEqualTo(NET_MASK);
    }

    @Test
    public void createShouldCreateCircuitFormWithCorrectPrimaryUtilityIpAddress() {
        // Given
        createStandardCircuitAndConfigData();
        // When
        CircuitForm circuitForm = circuitFormFactory.create(circuit, circuitConfigData);

        // Then
        assertThat(circuitForm).isNotNull();
        assertThat(circuitForm.getPrimaryUtilityIpAddress()).isEqualTo(PRIMARY_UTILITY_ADDRESS);
    }

    @Test
    public void createShouldCreateCircuitFormWithCorrectSecondaryUtilityIpAddress() {
        // Given
        createStandardCircuitAndConfigData();
        // When
        CircuitForm circuitForm = circuitFormFactory.create(circuit, circuitConfigData);

        // Then
        assertThat(circuitForm).isNotNull();
        assertThat(circuitForm.getSecondaryUtilityIpAddress()).isEqualTo(SECONDARY_UTILITY_ADDRESS);
    }

    @Test
    public void createShouldCreateCircuitFormWithCorrectRegion() {
        // Given
        createStandardCircuitAndConfigData();
        // When
        CircuitForm circuitForm = circuitFormFactory.create(circuit, circuitConfigData);

        // Then
        assertThat(circuitForm).isNotNull();
        assertThat(circuitForm.getRegion()).isEqualTo(REGION);
    }

    @Test
    public void createShouldCreateCircuitFormWithCorrectTrunkNumber() {
        // Given
        createStandardCircuitAndConfigData();
        // When
        CircuitForm circuitForm = circuitFormFactory.create(circuit, circuitConfigData);

        // Then
        assertThat(circuitForm).isNotNull();
        assertThat(circuitForm.getTrunkNumber()).isEqualTo(TRUNK_NUMBER);
    }

    private void createStandardCircuitAndConfigData() {
        circuit = Fixtures.createWSipLayerTwoCircuit();
        circuitConfigData = new HardCodedCircuitConfigData();
    }

    private void createCircuitWithNoGatewayAndConfigData() {
        circuit = Fixtures.createWSipLayerTwoCircuitWithoutGateway();
        circuitConfigData = new HardCodedCircuitConfigData();
    }

}
