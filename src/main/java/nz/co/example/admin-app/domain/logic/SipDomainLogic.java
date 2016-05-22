/**
 * 
 */
package nz.co.example.dev.domain.logic;

import java.util.List;

import nz.co.example.dev.domain.model.Circuit;
import nz.co.example.dev.mvc.CircuitForm;

/**
 * Provides domain logic for the sip models.
 * 
 * @author nivanov
 * 
 */
public interface SipDomainLogic {

    /**
     * Retrieves all of the {@link Circuit} elements.
     * @return
     */
    List<Circuit> getAllCircuits();

    /**
     * Creates a {@link CircuitForm} from a given {@link Circuit}.
     * 
     * @param circuit
     * @return circuitForm
     */
    CircuitForm createCircuitForm(Circuit circuit);
    
    /**
     * Creates a {@link Circuit} from a given {@link CircuitForm}.
     * 
     * @param circuitForm
     * @return circuit
     */
    Circuit createCircuit(CircuitForm circuitForm);

    /**
     * Checks whether a circuit with a given key exists.
     * 
     * @param circuitKey - the region and the access vlan
     * @return whether a circuit with a given key exists.
     */
    boolean circuitWithKeyExists(String circuitKey);
    
    /**
     * Adds a circuit.
     * 
     * @param circuit - the circuit to add.
     */
    void addCircuit(Circuit circuit);

    /**
     * Modifies a circuit.
     * 
     * @param circuit - the old circuit details.
     * @param circuit - the new circuit details.
     */
    void modifyCircuit(Circuit oldCircuit, Circuit newCircuit);

    /**
     * Removes a circuit.
     * 
     * @param circuit - the circuit to remove.
     */
    void removeCircuit(Circuit circuit);

}
