package somossuinos.aptcomplex.domain.exception;

/**
 * @author ceballos
 * @since 2015-07-22
 */
public class AptComplexException extends RuntimeException {

    public AptComplexException(final String message) {
        super(message);
    }

    public AptComplexException(final Throwable cause) {
        super(cause);
    }
}
