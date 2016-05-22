/**
 * 
 */
package nz.co.example.dev.domain.exception;

/**
 * Represents a validation exception that has somehow got passed the normal validation routines.
 * 
 * @author nivanov
 *
 */
public class ValidationException extends LogicException {

    private static final long serialVersionUID = -8009255888632666879L;

    public ValidationException(String message, Throwable t) {
        super(message, t);
    }

}
