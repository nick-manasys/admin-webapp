/**
 * 
 */
package nz.co.example.dev.domain.exception;

/**
 * Represents a exception which has occured during integration with another syste,m..
 * 
 * @author nivanov
 *
 */
public class IntegrationException extends RuntimeException {

    private static final long serialVersionUID = 8953146760166207459L;

    public IntegrationException(String message) {
        super(message);
    }

}
