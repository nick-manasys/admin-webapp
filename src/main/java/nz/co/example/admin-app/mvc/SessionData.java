/**
 * 
 */
package nz.co.example.dev.mvc;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * Stores the session data for the user's session.
 * 
 * @author nivanov
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionData implements Serializable{

    private static final long serialVersionUID = 1645967794683573301L;
    private CircuitForm circuitPreModifications;
    
    public CircuitForm getCircuitPreModifications() {
        return circuitPreModifications;
    }
    public void setCircuitPreModifications(CircuitForm circuitPreModifications) {
        this.circuitPreModifications = circuitPreModifications;
    }

}
