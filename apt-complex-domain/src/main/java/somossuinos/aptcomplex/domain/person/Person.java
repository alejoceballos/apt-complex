package somossuinos.aptcomplex.domain.person;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import somossuinos.aptcomplex.domain.person.document.TaxAndCreditDocument;

/**
 * @since 2015-07-17
 */

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    @Embedded
    private PersonName name = new PersonName();

    public PersonName getName() {
        return name;
    }

    public void setName(@NotNull final PersonName name) {
        this.name = name;
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
    @Column
    private Long version;

    protected Person() {
    }

    public Person(@NotNull final PersonName name) {
        this.name = name;
    }

    public Person(final String name) {
        this.name = new PersonName(name);
    }

}
