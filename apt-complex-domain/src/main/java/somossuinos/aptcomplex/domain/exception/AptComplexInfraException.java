package somossuinos.aptcomplex.domain.exception;

/**
 * @author ceballos
 * @since 2015-07-20
 */
public class AptComplexInfraException extends AptComplexException {

    public AptComplexInfraException(final String message) {
        super(message);
    }

    public AptComplexInfraException(final Throwable cause) {
        super(cause);
    }

}
