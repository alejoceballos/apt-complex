package somossuinos.aptcomplex.domain.person;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.AbstractPersistable;
import somossuinos.aptcomplex.domain.exception.AptComplexDomainException;
import somossuinos.aptcomplex.domain.exception.ExceptionMessages;
import somossuinos.aptcomplex.domain.person.document.TaxAndCreditDocument;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/**
 * @since 2015-07-17
 */

@JsonIgnoreProperties( { "new" })
@Entity
@Table(name = "person")
public class Person extends AbstractPersistable<Long> {

    @Embedded
    private PersonName name = new PersonName();

    public PersonName getName() {
        return name;
    }

    @Embedded
    private TaxAndCreditDocument document;

    public TaxAndCreditDocument getDocument() {
        return document;
    }

    public void setDocument(@NotNull final TaxAndCreditDocument document) {
        this.document = document;
    }

    @Version
    @Column(nullable = false)
    private Long version;

    protected Person() {
    }

    public static class Builder {
        private Person person;

        private Builder() {
            this.person = new Person();
        }

        public static Builder get(final String name) {
            if (StringUtils.isBlank(name)) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));
            final Builder builder = new Builder();
            builder.person.name = new PersonName(name);
            return builder;
        }

        public Builder withDocument(final String document) {
            if (StringUtils.isBlank(document)) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));
            person.document = new TaxAndCreditDocument(document);
            return this;
        }

        public Person build() {
            return person;
        }

    }
}
