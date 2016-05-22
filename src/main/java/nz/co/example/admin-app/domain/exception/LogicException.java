/**
 * 
 */
package nz.co.example.dev.domain.exception;

/**
 * Represents a logic exception.
 * 
 * @author nivanov
 *
 */
public class LogicException extends RuntimeException {

    private static final long serialVersionUID = 8953146760166207459L;

    public LogicException(String message) {
        super(message);
    }

    public LogicException(String message, Throwable t) {
        super(message, t);
    }

}
