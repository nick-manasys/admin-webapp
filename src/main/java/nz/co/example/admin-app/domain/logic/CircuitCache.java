/**
 * 
 */
package nz.co.example.dev.domain.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import nz.co.example.dev.domain.model.Circuit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * A cache of the circuits returned from the NCC.
 * 
 * @author nivanov
 */
@Service
public class CircuitCache {
    private static final Logger logger = LoggerFactory.getLogger(CircuitCache.class);

    private Map<String, Circuit> circuits = new ConcurrentHashMap<String, Circuit>();

    public Circuit getCircuit(String circuitKey) {
        return circuits.get(circuitKey);
    }

    public boolean containsCircuit(String circuitKey) {
        return circuits.containsKey(circuitKey);
    }

    public void addCircuits(List<Circuit> circuitList) {
        circuits.clear();
        for (Circuit circuit : circuitList) {
            if (circuits.containsKey(circuit.getKey())) {
                logger.debug("Circuit cache already contains key " + circuit.getKey());
            } else {
                circuits.put(circuit.getKey(), circuit);
            }
        }
    }

    public void addCircuit(Circuit circuit) {
        circuits.put(circuit.getKey(), circuit);
    }

    public void modifyCircuit(Circuit oldCircuit, Circuit newCircuit) {
        circuits.remove(oldCircuit.getKey());
        circuits.put(newCircuit.getKey(), newCircuit);
    }

    public void removeCircuit(Circuit circuit) {
        circuits.remove(circuit.getKey());
    }

    public boolean isEmpty() {
        return circuits.isEmpty();
    }

    public List<Circuit> getCircuits() {
        return new ArrayList<Circuit>(circuits.values());
    }
}
