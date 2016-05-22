package nz.co.example.dev.integration.operation;

import nz.co.example.dev.integration.LogEntry;

import com.google.common.base.Objects;

/**
 *
 */
public class AddWSipLayerTwoCircuit extends AbstractBaseOperationImpl {

    /**
     * 
     */
    private static final long serialVersionUID = 7356612111705934309L;

    public AddWSipLayerTwoCircuit(Object operand) {
        super(OperationType.ADD, operand);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getOperands().get(0).hashCode());
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof AddWSipLayerTwoCircuit) {
            final AddWSipLayerTwoCircuit other = (AddWSipLayerTwoCircuit) obj;
            return Objects.equal(this.getOperands().get(0), other.getOperands().get(0)); // && Objects.equal(children,
            // other.children);
        } else {
            return false;
        }
    }
}
