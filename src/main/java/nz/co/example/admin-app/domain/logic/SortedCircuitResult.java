/**
 * 
 */
package nz.co.example.dev.domain.logic;

import java.util.ArrayList;
import java.util.List;

import nz.co.example.dev.domain.model.Circuit;

/**
 * Represents the result from sorting circuit subcomponents into circuits.
 * 
 * Has the sorted circuits, and the left over subcomponents that could not be sorted.
 *  
 * @author nivanov
 *
 */
public class SortedCircuitResult<T extends Circuit> {
    
    private List<T> sortedCircuits;
    private CircuitSubComponents unsortedSubComponents;
    
    public SortedCircuitResult() {
        sortedCircuits = new ArrayList<T>();
    }
    
    public List<T> getSortedCircuits() {
        return sortedCircuits;
    }
    
    public void addSortedCircuits(List<T> sortedCircuits) {
        this.sortedCircuits.addAll(sortedCircuits);
    }

    public CircuitSubComponents getUnsortedSubComponents() {
        return unsortedSubComponents;
    }

    public void setUnsortedSubComponents(CircuitSubComponents unsortedSubComponents) {
        this.unsortedSubComponents = unsortedSubComponents;
    }
    
}
