package it.nz.co.example.dev.integration;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import nz.co.example.dev.integration.NNCServiceImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Nick
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class NNCServiceImplIT {
    private NNCServiceImpl service;

    @Before
    public void setUp() throws Exception {
        // SUCCESS
    }

    @After
    public void tearDown() throws Exception {
        // SUCCESS
    }

    @Test
    public final void testPostConstruct() throws Exception {
        NNCServiceImpl spy = Mockito.spy(service = new NNCServiceImpl());

        when(spy.getNncServerAddress()).thenReturn("10.66.4.9");
        when(spy.getNncServerPort()).thenReturn("8080");
        when(spy.getNncServerUsername()).thenReturn("devuser");
        when(spy.getNncServerPassword()).thenReturn("pass4321");

        try {
            spy.postConstruct();
            // SUCCESS
        } catch (Throwable e) {
            fail();
        }
    }
}
