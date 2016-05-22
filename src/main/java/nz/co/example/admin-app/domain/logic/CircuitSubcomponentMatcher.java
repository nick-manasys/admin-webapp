/**
 * 
 */
package nz.co.example.dev.domain.logic;

import nz.co.example.dev.domain.model.Circuit;

/**
 * Capable of sorting all of the circuit subcomponents to match them up into circuits.
 * 
 * Specific implementations of this will be able to build a list o specific Circuit Types
 * given a collection of various circuit subcomponents.
 * 
 * @author nivanov
 * 
 */
public interface CircuitSubcomponentMatcher<T extends Circuit> {

    /**
     * Matches the given subcomponents together and creates the list of circuit types.
     * 
     * The returned SortedCircuitResult has the sorted circuits, and the left over subcomponents that could not be
     * sorted.
     * 
     * @param subComponents
     * @return
     */
    SortedCircuitResult<T> matchSubComponents(CircuitSubComponents subComponents);

}
