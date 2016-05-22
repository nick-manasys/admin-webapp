package test.nz.co.example.dev.domain.model;

import static nz.co.example.dev.domain.model.NatTraversal.ALWAYS;
import static nz.co.example.dev.domain.model.NatTraversal.NONE;
import static org.fest.assertions.Assertions.assertThat;
import nz.co.example.dev.domain.model.NatTraversal;

import org.junit.Test;

public class NatTraversalTest {

    @Test
    public void shouldCreateCorrectToStringWhenAlways() {
        NatTraversal natTraversal = ALWAYS;
        assertThat(natTraversal.toString()).isEqualTo("nat-traversal always");
    }

    @Test
    public void shouldCreateCorrectToStringWhenNone() {
        NatTraversal natTraversal = NONE;
        assertThat(natTraversal.toString()).isEqualTo("nat-traversal none");
    }

    @Test
    public void shouldCreateCorrectToStringWhenRPort() {
        NatTraversal natTraversal = NatTraversal.RPORT;
        assertThat(natTraversal.toString()).isEqualTo("nat-traversal rport");
    }

}
