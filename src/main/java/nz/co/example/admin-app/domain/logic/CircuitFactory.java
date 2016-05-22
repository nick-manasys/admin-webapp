/**
 * 
 */
package nz.co.example.dev.domain.logic;

import nz.co.example.dev.domain.model.Circuit;
import nz.co.example.dev.mvc.CircuitForm;

/**
 * 
 * A factory class for creating circuits.
 * 
 * @author nivanov
 */
public interface CircuitFactory<T extends Circuit> {

    boolean createsType(CircuitForm circuitForm);

    T create(CircuitForm circuitForm, CircuitConfigData circuitConfigData);

}
