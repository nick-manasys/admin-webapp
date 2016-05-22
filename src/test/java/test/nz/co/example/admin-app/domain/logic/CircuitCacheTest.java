package test.nz.co.example.dev.domain.logic;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import nz.co.example.dev.domain.logic.CircuitCache;
import nz.co.example.dev.domain.model.BaseCircuit;
import nz.co.example.dev.domain.model.Circuit;
import nz.co.example.dev.domain.model.CircuitType;

import org.junit.Test;

public class CircuitCacheTest {

    private CircuitCache circuitCache = new CircuitCache();

    @Test
    public void getCircuitGivenNoCircuitsShoudReturnNull() {
        // Given

        // When
        Circuit circuit = circuitCache.getCircuit("key-1");

        // Then
        assertThat(circuit).isNull();
    }

    @Test
    public void getCircuitGivenCircuitsShouldReturnThem() {
        // Given
        Circuit testCircuitOne = new BaseCircuit("tux01", "218.101.10.1", "1", "auc", CircuitType.UNKNOWN);
        Circuit testCircuitTwo = new BaseCircuit("tux02", "218.101.10.1", "2", "auc", CircuitType.UNKNOWN);
        circuitCache.addCircuits(Arrays.asList(testCircuitOne, testCircuitTwo));
        // When
        Circuit circuitOne = circuitCache.getCircuit("auc-1");
        Circuit circuitTwo = circuitCache.getCircuit("auc-2");

        // Then
        assertThat(circuitOne).isEqualTo(testCircuitOne);
        assertThat(circuitTwo).isEqualTo(testCircuitTwo);
    }

    @Test
    public void addCircuitsGivenCircuitsShouldClearExistingCircuits() {
        // Given
        Circuit testCircuitOne = new BaseCircuit("tux01", "218.101.10.1", "1", "auc", CircuitType.UNKNOWN);
        circuitCache.addCircuits(Arrays.asList(testCircuitOne));
        assertThat(circuitCache.containsCircuit("auc-1")).isTrue();

        // When
        Circuit testCircuitTwo = new BaseCircuit("tux02", "218.101.10.1", "2", "auc", CircuitType.UNKNOWN);
        circuitCache.addCircuits(Arrays.asList(testCircuitTwo));

        // Then
        assertThat(circuitCache.containsCircuit("auc-1")).isFalse();
        assertThat(circuitCache.containsCircuit("auc-2")).isTrue();
    }

    @Test
    public void containsCircuitGivenAnExistingCircuitKeyShouldReturnTrue() {
        // Given
        Circuit testCircuitOne = new BaseCircuit("tux01", "218.101.10.1", "1", "auc", CircuitType.UNKNOWN);
        circuitCache.addCircuits(Arrays.asList(testCircuitOne));

        // When
        boolean containsCircuit = circuitCache.containsCircuit("auc-1");

        // Then
        assertThat(containsCircuit).isTrue();
    }

    @Test
    public void containsCircuitGivenAnNonExistingCircuitKeyShouldReturnTrue() {
        // Given
        Circuit testCircuitOne = new BaseCircuit("tux01", "218.101.10.1", "1", "auc", CircuitType.UNKNOWN);
        circuitCache.addCircuits(Arrays.asList(testCircuitOne));

        // When
        boolean containsCircuit = circuitCache.containsCircuit("auc-2");

        // Then
        assertThat(containsCircuit).isFalse();
    }

    /**
     * Added unit test after change to getKey of circuit.
     * 
     * @throws Exception
     */
    @Test
    public void testAddCircuitsShouldReportDuplicateKey() throws Exception {
        // Given
        Circuit testCircuitOne = new BaseCircuit("tux01", "192.168.1.1", "1", "auc", CircuitType.W_SIP_LAYER_TWO);
        Circuit testCircuitTwo = new BaseCircuit("tux02", "192.168.1.2", "2", "auc", CircuitType.W_SIP_LAYER_TWO);
        Circuit testCircuitThree = new BaseCircuit("tux01", "192.168.1.1", "1", "auc", CircuitType.W_SIP_LAYER_TWO);
        circuitCache.addCircuits(Arrays.asList(testCircuitOne, testCircuitTwo, testCircuitThree));

        assertEquals("Expect 2 elements ", 2, circuitCache.getCircuits().size());
    }
}
