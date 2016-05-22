package nz.co.example.dev.integration.tx;

import java.util.ArrayList;
import java.util.List;

import nz.co.example.dev.integration.operation.Operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;

/**
 * @author Nick
 * 
 */
public class NNCTransaction extends AbstractPlatformTransactionManager {
    private static final Logger logger = LoggerFactory.getLogger(NNCTransaction.class);

    /**
     * 
     */
    private static final long serialVersionUID = 3390557738629598732L;

    /**
     * 
     */
    private ArrayList<Operation> operations = new ArrayList<Operation>();

    /**
     * 
     */
    public NNCTransaction() {
        logger.debug("NNCTransaction");
    }

    @Override
    public void doBegin(Object arg0, TransactionDefinition arg1) throws TransactionException {
        logger.debug("doBegin");
    }

    @Override
    public void doCommit(DefaultTransactionStatus arg0) throws TransactionException {
        logger.debug("doCommit");
    }

    @Override
    public Object doGetTransaction() throws TransactionException {
        logger.debug("doGetTransaction");
        return null;
    }

    @Override
    public void doRollback(DefaultTransactionStatus arg0) throws TransactionException {
        logger.debug("doRollback");
    }

    public List<Operation> getOperations() {
        return operations;
    }
}
