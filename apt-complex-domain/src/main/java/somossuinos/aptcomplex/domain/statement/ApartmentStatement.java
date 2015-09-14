package somossuinos.aptcomplex.domain.statement;

import org.springframework.data.jpa.domain.AbstractPersistable;
import somossuinos.aptcomplex.domain.apartment.Apartment;
import somossuinos.aptcomplex.domain.exception.AptComplexDomainException;
import somossuinos.aptcomplex.domain.exception.ExceptionMessages;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by ceballos on 31/08/15.
 */
@Entity
@Table(name = "apartment_statement")
public class ApartmentStatement  extends AbstractPersistable<Long> {

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH )
    @JoinColumn(name = "apartment_id", nullable = true)
    private Apartment apartment;

    public Apartment getApartment() {
        return apartment;
    }

    @NotNull
    @Size(max = 4)
    @Column(name = "apartment_number", nullable = false, length = 4)
    private String apartmentNumber;

    @NotNull
    @Column(name = "fee_paid", nullable = false)
    private Boolean feePaid = false;

    public Boolean getFeePaid() {
        return feePaid;
    }

    @NotNull
    @Column(name = "surcharge_paid", nullable = false)
    private Boolean surchargePaid = false;

    public Boolean getSurchargePaid() {
        return surchargePaid;
    }

    protected ApartmentStatement() {
    }

    public static class Builder {
        private ApartmentStatement statement;

        private Builder() {
            this.statement = new ApartmentStatement();
        }

        public static Builder get(final Apartment apartment) {
            if (apartment == null) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));
            final Builder builder = new Builder();
            builder.statement.apartment = apartment;
            builder.statement.apartmentNumber = apartment.getNumber();
            return builder;
        }

        public Builder withFeePaid() {
            statement.feePaid = true;
            return this;
        }

        public Builder withSurchargePaid() {
            statement.surchargePaid = true;
            return this;
        }

        public ApartmentStatement build() {
            return statement;
        }
    }

}
