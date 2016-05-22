package test.nz.co.example.dev.domain.logic;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.verify;

import java.util.Iterator;
import java.util.List;

import nz.co.example.dev.domain.logic.CircuitSubComponentSorter;
import nz.co.example.dev.domain.logic.CircuitSubComponents;
import nz.co.example.dev.domain.logic.CircuitSubcomponentMatcher;
import nz.co.example.dev.domain.logic.SortedCircuitResult;
import nz.co.example.dev.domain.model.Circuit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CircuitSubComponentSorterTest {

    @Mock
    private List<CircuitSubcomponentMatcher<Circuit>> subcomponentMatchers;

    @Mock
    private Iterator<CircuitSubcomponentMatcher<Circuit>> subcomponentIterator;

    @Mock
    private CircuitSubcomponentMatcher<Circuit> firstMatcher;

    @Mock
    private CircuitSubcomponentMatcher<Circuit> secondMatcher;

    @Mock
    private CircuitSubComponents circuitSubComponents;

    @Mock
    private SortedCircuitResult<Circuit> firstMatchedSubComponents;

    @Mock
    private SortedCircuitResult<Circuit> secondMatchedSubComponents;

    @InjectMocks
    private CircuitSubComponentSorter circuitSubComponentSorter;

    @Test
    public void getSortedCircuitsGivenSingleMatcherWillMatchSubcomponents() {
        // Given
        given(subcomponentMatchers.iterator()).willReturn(subcomponentIterator);
        given(subcomponentIterator.hasNext()).willReturn(true, false);
        given(subcomponentIterator.next()).willReturn(firstMatcher);

        SortedCircuitResult<Circuit> typedMatchedSubComponents = new SortedCircuitResult<Circuit>();

        given(firstMatcher.matchSubComponents(circuitSubComponents)).willReturn(typedMatchedSubComponents);

        // When
        SortedCircuitResult<Circuit> sortedCircuits = circuitSubComponentSorter.getSortedCircuits(circuitSubComponents);

        // Then
        assertThat(sortedCircuits).isEqualTo(typedMatchedSubComponents);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getSortedCircuitsGivenMultipleMatchersWillMatchSubcomponents() {
        // Given
        given(subcomponentMatchers.iterator()).willReturn(subcomponentIterator);
        given(subcomponentIterator.hasNext()).willReturn(true, true, false);
        given(subcomponentIterator.next()).willReturn(firstMatcher, secondMatcher);

        given(firstMatcher.matchSubComponents(circuitSubComponents)).willReturn(firstMatchedSubComponents);

        given(firstMatchedSubComponents.getUnsortedSubComponents()).willReturn(circuitSubComponents);

        given(secondMatcher.matchSubComponents(circuitSubComponents)).willReturn(secondMatchedSubComponents);

        // When
        SortedCircuitResult<Circuit> sortedCircuits = circuitSubComponentSorter.getSortedCircuits(circuitSubComponents);

        // Then
        verify(firstMatchedSubComponents).addSortedCircuits(anyList());
        verify(firstMatchedSubComponents).setUnsortedSubComponents(any(CircuitSubComponents.class));
        assertThat(sortedCircuits).isEqualTo(firstMatchedSubComponents);
    }
}
