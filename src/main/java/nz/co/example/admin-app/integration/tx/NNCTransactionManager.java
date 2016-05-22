package nz.co.example.dev.integration.tx;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;

/**
 * @author Nick
 * 
 */
public class NNCTransactionManager extends AbstractPlatformTransactionManager implements PlatformTransactionManager {
    private static final Logger logger = LoggerFactory.getLogger(NNCTransactionManager.class);

    /**
     * 
     */
    private static final long serialVersionUID = 7942625136456798110L;

    private NNCTransaction currentTransaction = null;

    private Set<Object> transactions = new HashSet<Object>();

    private boolean isEnabled() {
        return false && logger.isDebugEnabled();
    }

    @Override
    public boolean isExistingTransaction(Object transaction) throws TransactionException {
        boolean result;
        if (transactions.contains(transaction)) {
            result = true;
        } else {
            result = false;
            transactions.add(transaction);
        }
        return super.isExistingTransaction(transaction);
    }

    @Override
    public Object doGetTransaction() throws TransactionException {
        if (isEnabled()) {
            logger.debug("--> NNCTransactionManager.doGetTransaction()");
        }
        if (this.isNestedTransactionAllowed() || null == this.currentTransaction) {
            currentTransaction = new NNCTransaction();
        }
        return currentTransaction;
    }

    @Override
    public void doBegin(Object paramObject, TransactionDefinition paramTransactionDefinition)
            throws TransactionException {
        if (isEnabled()) {
            logger.debug(">>> NNCTransactionManager.doBegin()");
        }
    }

    @Override
    public void doCommit(DefaultTransactionStatus paramDefaultTransactionStatus) throws TransactionException {
        if (isEnabled()) {
            logger.debug("<<< NNCTransactionManager.doCommit()");
        }
    }

    @Override
    public void doRollback(DefaultTransactionStatus paramDefaultTransactionStatus) throws TransactionException {
        if (isEnabled()) {
            logger.debug("<<< NNCTransactionManager.doRollback()");
        }
    }
}
