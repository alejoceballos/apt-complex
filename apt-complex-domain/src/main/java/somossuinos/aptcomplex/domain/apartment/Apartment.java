package somossuinos.aptcomplex.domain.apartment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.AbstractPersistable;
import somossuinos.aptcomplex.domain.finance.bill.Bill;
import somossuinos.aptcomplex.domain.person.Person;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collections;
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

    @Override
    protected void setId(Long id) {
        super.setId(id);
    }

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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "apartment_fee",
            joinColumns = @JoinColumn(name = "apartment_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "bill_id", nullable = false)
    )
    private Set<Bill> fees = new HashSet<>(0);

    public Set<Bill> getFees() {
        return fees;
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

        public static Builder get() {
            return new Builder();
        }

        public Apartment build() {
            return apartment;
        }

        public Builder withId(final Long id) {
            apartment.setId(id);
            return this;
        }

        public Builder withNumber(final String number) {
            apartment.number = number;
            return this;
        }

        public Builder withResident(final Person resident) {
            apartment.residents.add(resident);
            return this;
        }

        public Builder withFee(final Bill fee) {
            apartment.fees.add(fee);
            return this;
        }

        public Builder withFees(final Set<Bill> fees) {
            for (Bill fee : fees) {
                apartment.fees.add(fee);
            }

            return this;
        }
    }

}
