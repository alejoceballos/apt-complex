package somossuinos.aptcomplex.domain.person;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.AbstractPersistable;
import somossuinos.aptcomplex.domain.person.document.TaxAndCreditDocument;

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

        public static Builder get() {
            return new Builder();
        }

        public Person build() {
            return person;
        }

        public Builder withName(final String name) {
            person.name = new PersonName(name);
            return this;
        }

        public Builder withDocument(final String document) {
            person.document = new TaxAndCreditDocument(document);
            return this;
        }

    }
}
