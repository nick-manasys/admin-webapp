package nz.co.example.dev.integration.operation;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NNC Service is used to communicate with the NNC Server.
 * 
 * @author nivanov
 * 
 */
public abstract class AbstractBaseOperationImpl implements Operation {
    /**
     * 
     */
    private static final long serialVersionUID = -6106229868981694933L;

    private static final Logger logger = LoggerFactory.getLogger(AbstractBaseOperationImpl.class);

    private OperationType operationType;

    private List<Object> operands = new ArrayList<Object>();

    public AbstractBaseOperationImpl(OperationType operationType, List<Object> operands) {
        logger.debug("AbstractBaseOperationImpl: " + operationType + " " + operands);
        this.operationType = operationType;
        this.operands = operands;
    }

    public AbstractBaseOperationImpl(OperationType operationType, Object operand) {
        logger.debug("AbstractBaseOperationImpl: " + operationType + " " + operand);
        this.operationType = operationType;
        this.operands.add(operand);
    }

    public AbstractBaseOperationImpl(OperationType operationType, Object... operands) {
        logger.debug("AbstractBaseOperationImpl: " + operationType + " " + operands);
        this.operationType = operationType;
        for (Object operand : operands) {
            this.operands.add(operand);
        }
    }

    @Override
    public OperationType getOperationType() {
        return operationType;
    }

    @Override
    public List<Object> getOperands() {
        return operands;
    }

    public String toString() {
        StringBuffer result = new StringBuffer(this.getOperationType().toString());
        for (Object operand : this.operands) {
            if (null != operand) {
                result = result.append(" ");
                result = result.append(operand.toString());
            }
        }
        return result.toString();
    }
}