package test.nz.co.example.dev.integration.tx;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import nz.co.example.dev.integration.tx.NNCTransaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionStatus;

public class NNCTransactionTest {
    private NNCTransaction tx;

    @Before
    public void setUp() throws Exception {
        tx = new NNCTransaction();
    }

    @After
    public void tearDown() throws Exception {
        // EMPTY
    }

    @Test
    public final void testNNCTransaction() throws Exception {
        assertNotNull(tx);
        TransactionDefinition transactionDefinition = null;
        TransactionStatus transactionStatus = tx.getTransaction(transactionDefinition);
        assertNotNull(transactionStatus);
    }

    @Test
    public final void testDoBegin() throws Exception {
        Object object = null;
        TransactionDefinition transactionDefinition = null;
        try {
            tx.doBegin(object, transactionDefinition);
            // SUCCESS
        } catch (Exception e) {
            fail("tx.doBegin()");
        }
    }

    @Test
    public final void testDoCommit() throws Exception {
        DefaultTransactionStatus defaultTransactionStatus = null;
        tx.doCommit(defaultTransactionStatus);
    }

    @Test
    public final void testDoGetTransaction() throws Exception {
        TransactionDefinition transactionDefinition = null;
        tx.getTransaction(transactionDefinition);
    }

    @Test
    public final void testDoRollback() throws Exception {
        DefaultTransactionStatus defaultTransactionStatus = null;
        tx.doRollback(defaultTransactionStatus);
    }
}
