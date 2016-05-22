package nz.co.example.dev.integration.operation;

import java.io.Serializable;
import java.util.List;

/**
 * NNC Service is used to communicate with the NNC Server.
 * 
 * @author Nick
 * 
 */
public interface Operation extends Serializable {
    enum OperationType {
        ADD, DELETE, GETALL, MODIFY,
    }

    OperationType getOperationType();

    List<Object> getOperands();
}