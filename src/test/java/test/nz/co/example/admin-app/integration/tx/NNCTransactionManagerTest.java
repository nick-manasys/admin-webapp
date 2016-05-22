package test.nz.co.example.dev.integration.tx;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import nz.co.example.dev.integration.tx.NNCTransactionManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;

public class NNCTransactionManagerTest {
    private NNCTransactionManager txManager;

    @Before
    public void setUp() throws Exception {
        txManager = new NNCTransactionManager();
    }

    @After
    public void tearDown() throws Exception {
        // EMPTY
    }

    @Test
    public final void testDoGetTransaction() throws Exception {
        Object tx = txManager.doGetTransaction();
        assertNotNull(tx);
    }

    @Test
    public final void testDoBegin() throws Exception {
        Object paramObject = null;
        TransactionDefinition paramTransactionDefinition = null;
        txManager.doBegin(paramObject, paramTransactionDefinition);
    }

    @Test
    public final void testDoCommit() throws Exception {
        DefaultTransactionStatus paramDefaultTransactionStatus = null;
        try {
            txManager.doCommit(paramDefaultTransactionStatus);
        } catch (Throwable e) {
            fail("Rollback");
        }
    }

    @Test
    public final void testDoRollback() throws Exception {
        DefaultTransactionStatus paramDefaultTransactionStatus = null;
        try {
            txManager.doRollback(paramDefaultTransactionStatus);
        } catch (Throwable e) {
            fail("Rollback");
        }
    }
}
