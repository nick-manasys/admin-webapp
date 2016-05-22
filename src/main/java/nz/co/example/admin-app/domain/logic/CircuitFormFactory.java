/**
 * 
 */
package nz.co.example.dev.domain.logic;

import nz.co.example.dev.domain.model.Circuit;
import nz.co.example.dev.mvc.CircuitForm;

/**
 * Factory capable of creating a circuit entry form from the W-SIP Layer Two circuit data and the config data.
 * 
 * @author nivanov
 *
 */
public interface CircuitFormFactory<T extends Circuit> {

    boolean createsFromType(Circuit circuit);
    CircuitForm create(T circuit, CircuitConfigData circuitConfigData);

}
