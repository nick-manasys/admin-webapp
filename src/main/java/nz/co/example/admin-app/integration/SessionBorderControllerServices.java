/**
 * 
 */
package nz.co.example.dev.integration;

import nz.co.example.dev.domain.logic.CircuitSubComponents;
import nz.co.example.dev.domain.model.Circuit;

/**
 * Provides the integration with the dev (Session Border Controllers).
 * 
 * This facade like class provides a layer of abstraction between the
 * rest of the application and the actual implementation of the
 * dev management integration layer.
 * 
 * @author nivanov
 * 
 */
public interface SessionBorderControllerServices {
    String getLoginBanner();

    CircuitSubComponents getAllCircuitSubComponents();

    void addNewCircuit(Circuit circuit);

    void modifyCircuit(Circuit oldCircuit, Circuit newCircuit);

    void relinquishCircuit(Circuit circuit);

}
