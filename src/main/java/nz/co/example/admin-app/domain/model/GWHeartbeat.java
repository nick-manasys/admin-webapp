/**
 * 
 */
package nz.co.example.dev.domain.model;

import java.io.Serializable;

import com.google.common.base.Objects;

/**
 * The Network Interface.
 * 
 * This encapsulates the configured values for a:
 * 
 * system->network-interface sub-element within the dev.
 * 
 * @author nivanov
 */
public class GWHeartbeat implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4769307632520552412L;

    private State state;

    public static GWHeartbeat createDisabled() {
        return new GWHeartbeat(State.DISABLED);
    }

    public static GWHeartbeat createEnabled() {
        return new GWHeartbeat(State.ENABLED);
    }

    private GWHeartbeat(State state) {
        super();
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return String.format("gw-heartbeat%n%s", state);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(state);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof GWHeartbeat)) {
            return false;
        }
        GWHeartbeat other = (GWHeartbeat) obj;
        return Objects.equal(state, other.getState());
    }

}
