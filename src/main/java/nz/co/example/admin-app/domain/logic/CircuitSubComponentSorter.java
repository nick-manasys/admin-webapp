/**
 * 
 */
package nz.co.example.dev.domain.logic;

import java.util.List;

import nz.co.example.dev.domain.model.Circuit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Capable of sorting all of the circuit subcomponents to match them up into circuits.
 * 
 * Is injected with a list of actual @see {@link CircuitSubcomponentMatcher} implementations
 * which do the actual matching of the various subcomponents into full circuits.
 * 
 * The function of this class is to facilitate the matching and produce one single list of all circuits
 * and the left over subcomponents that could not be matched.
 * 
 * @author nivanov
 */
@Service
public class CircuitSubComponentSorter {
    
    @Autowired
    private List<CircuitSubcomponentMatcher<Circuit>> circuitSubcomponentMatchers;
   
    public SortedCircuitResult<Circuit> getSortedCircuits(CircuitSubComponents circuitSubComponents)    
    {
        SortedCircuitResult<Circuit> matchedSubComponents = null;
        for (CircuitSubcomponentMatcher<Circuit> subcomponentMatcher : circuitSubcomponentMatchers) {
            if (matchedSubComponents == null)
            {
                matchedSubComponents = subcomponentMatcher.matchSubComponents(circuitSubComponents);
                
            } else {
                SortedCircuitResult<Circuit> typedMatchedSubComponents = subcomponentMatcher.matchSubComponents(matchedSubComponents.getUnsortedSubComponents());
                matchedSubComponents.addSortedCircuits(typedMatchedSubComponents.getSortedCircuits());
                matchedSubComponents.setUnsortedSubComponents(typedMatchedSubComponents.getUnsortedSubComponents());
            }
        }
        
        return matchedSubComponents;
    }
    
}
