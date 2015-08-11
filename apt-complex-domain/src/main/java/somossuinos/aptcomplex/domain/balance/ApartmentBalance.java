package somossuinos.aptcomplex.domain.balance;

import org.springframework.data.jpa.domain.AbstractPersistable;
import somossuinos.aptcomplex.domain.apartment.Apartment;
import somossuinos.aptcomplex.domain.bill.BillWithDueDate;
import somossuinos.aptcomplex.domain.exception.AptComplexDomainException;
import somossuinos.aptcomplex.domain.exception.ExceptionMessages;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ceballos
 * @since 2015-08-04
 */
@Entity
@Table(name = "apartment_balance")
public class ApartmentBalance extends AbstractPersistable<Long> {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH )
    @JoinColumn(name = "apartment_id", nullable = true)
    private Apartment apartment;

    public Apartment getApartment() {
        return apartment;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "apartment_fee",
            joinColumns = @JoinColumn(name = "apartment_balance_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "bill_id", nullable = false)
    )
    private Set<BillWithDueDate> fees = new HashSet<>(0);

    public Set<BillWithDueDate> getFees() {
        return fees;
    }

    @Column(name = "apartment_number", nullable = false, length = 4)
    private String apartmentNumber;

    protected ApartmentBalance() {
    }

    public static class Builder {
        private ApartmentBalance balance;

        private Builder() {
            this.balance = new ApartmentBalance();
        }

        public static Builder get(final Apartment apartment) {
            if (apartment == null) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));
            final Builder builder = new Builder();
            builder.balance.apartment = apartment;
            builder.balance.apartmentNumber = apartment.getNumber();
            return builder;
        }

        public Builder withFee(final BillWithDueDate fee) {
            if (fee == null) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));
            balance.fees.add(fee);
            return this;
        }

        public ApartmentBalance build() {
            return balance;
        }
    }

}
