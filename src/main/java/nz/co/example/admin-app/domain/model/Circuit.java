/**
 * 
 */
package nz.co.example.dev.domain.model;

import java.io.Serializable;

/**
 * A circuit.
 * 
 * @author nivanov
 */
public interface Circuit extends Serializable {

    String getCarrierId();

    String getIpAddress();

    String getVLAN();

    String getRegion();

    CircuitType getCircuitType();

    String getKey();

}
