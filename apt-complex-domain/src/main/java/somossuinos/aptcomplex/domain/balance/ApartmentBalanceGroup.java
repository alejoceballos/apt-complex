package somossuinos.aptcomplex.domain.balance;

import org.apache.commons.collections.CollectionUtils;
import somossuinos.aptcomplex.domain.exception.AptComplexDomainException;
import somossuinos.aptcomplex.domain.exception.ExceptionMessages;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author ceballos
 * @since 2015-08-04
 */
@Entity
@DiscriminatorValue("INCOMES")
@Table(name = "apartment_balance_group")
public class ApartmentBalanceGroup extends EntityBalanceGroup {

    @Size(max = 255)
    @Column(name = "description", length = 255)
    protected String description;

    public String getDescription() {
        return description;
    }

    @NotNull
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "apartment_balance_group_id", nullable = false)
    @MapKeyColumn(name = "apartment_number")
    private Map<String, ApartmentBalance> apartmentsBalance = new HashMap<>(0);

    public Map<String, ApartmentBalance> getApartmentsBalance() {
        return apartmentsBalance;
    }

    protected ApartmentBalanceGroup() {
    }

    protected ApartmentBalanceGroup(final BalanceType type) {
        super(type);
    }

    public static class Builder extends EntityBalanceGroup.Builder {

        private Builder() {
            super.group = new ApartmentBalanceGroup(BalanceType.INCOMES);
        }

        public static Builder get() {
            return new Builder();
        }

        public Builder withApartmentBalance(final ApartmentBalance balance) {
            if (balance == null) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));
            ((ApartmentBalanceGroup) group).apartmentsBalance.put(balance.getApartment().getNumber(), balance);
            return this;
        }

        public Builder withApartmentBalances(final Set<ApartmentBalance> balances) {
            if (CollectionUtils.isEmpty(balances)) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));
            for (final ApartmentBalance balance : balances) {
                ((ApartmentBalanceGroup) group).apartmentsBalance.put(balance.getApartment().getNumber(), balance);
            }
            return this;
        }

        public Builder withDescription(final String description) {
            if (description == null) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));
            ((ApartmentBalanceGroup) group).description = description;
            return this;
        }

        public Builder withDivergence(final BalanceDivergence divergence) {
            super.withDivergence(divergence);
            return this;
        }

        public ApartmentBalanceGroup build() {
            return (ApartmentBalanceGroup) group;
        }
    }
}
