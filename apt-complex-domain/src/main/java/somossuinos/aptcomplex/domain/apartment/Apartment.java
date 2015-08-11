package somossuinos.aptcomplex.domain.apartment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.AbstractPersistable;
import somossuinos.aptcomplex.domain.exception.AptComplexDomainException;
import somossuinos.aptcomplex.domain.exception.ExceptionMessages;
import somossuinos.aptcomplex.domain.person.Person;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ceballos
 * @since 2015-07-17
 */
@JsonIgnoreProperties( { "new" })
@Entity
@Table(name = "apartment")
public class Apartment extends AbstractPersistable<Long> {

    @NotNull
    @Column(name = "number", nullable = false, length = 4, unique = true)
    private String number;

    public String getNumber() {
        return number;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "residence",
            joinColumns = @JoinColumn(name = "apartment_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "person_id", nullable = false)
    )
    private Set<Person> residents = new HashSet<>(0);

    public Set<Person> getResidents() {
        return residents;
    }

    @NotNull
    @Column(name = "version", nullable = false)
    @Version
    private Long version;

    protected Apartment() {
    }

    public static class Builder {
        private Apartment apartment;

        private Builder() {
            this.apartment = new Apartment();
        }

        public static Builder get(final String number) {
            if (StringUtils.isBlank(number)) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));
            final Builder builder = new Builder();
            builder.apartment.number = number;
            return builder;
        }

        public Apartment build() {
            return apartment;
        }

        public Builder withResident(final Person resident) {
            if (resident == null) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));
            apartment.residents.add(resident);
            return this;
        }

    }

}
