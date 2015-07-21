package somossuinos.aptcomplex.domain.person.document;

import com.danielfariati.validator.CPFValidator;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import somossuinos.aptcomplex.domain.exception.AptComplexDomainException;

/**
 * @author ceballos
 * @since 2015-07-20
 */
@Embeddable
public class TaxAndCreditDocument {

    private static final CPFValidator VALIDATOR = new CPFValidator();

    @Column(name = "document", length = 11)
    private String number;

    public String getNumber() {
        return number;
    }

    protected TaxAndCreditDocument() {
    }

    public TaxAndCreditDocument(final String number) {
        if (!VALIDATOR.isValid(number, null)) {
            throw new AptComplexDomainException("Invalid document");
        }

        this.number = number;
    }

    public String getFormattedNumber() {
        return number.substring(0, 3).concat(".")
                .concat(number.substring(3, 6)).concat(".")
                .concat(number.substring(6, 9)).concat("-")
                .concat(number.substring(9, 11));
    }

}
