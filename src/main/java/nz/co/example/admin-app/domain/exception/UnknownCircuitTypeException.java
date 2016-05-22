package nz.co.example.dev.domain.exception;

/**
 * Thrown when we get a circuit type that the application has not been created to handle.
 * 
 * @author nivanov
 */
public class UnknownCircuitTypeException extends LogicException {
    
    private static final long serialVersionUID = 7675100339637503770L;

    public UnknownCircuitTypeException(String message) {
        super(message);
    }

}
