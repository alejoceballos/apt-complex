package somossuinos.aptcomplex.domain.balance;

import org.springframework.data.jpa.domain.AbstractPersistable;
import somossuinos.aptcomplex.domain.exception.AptComplexDomainException;
import somossuinos.aptcomplex.domain.exception.ExceptionMessages;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ceballos
 * @since 2015-08-03
 */
@Entity
@Table(name = "monthly_balance")
public class MonthlyBalance extends AbstractPersistable<Long> {

    @NotNull
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "monthly_balance_id", nullable = false)
    @MapKeyColumn(name = "type")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<BalanceType, EntityBalanceGroup> balanceGroups = new HashMap<>();

    public Map<BalanceType, EntityBalanceGroup> getBalanceGroups() {
        return balanceGroups;
    }

    @NotNull
    @Embedded
    private ReferenceMonth referenceMonth;

    public ReferenceMonth getReferenceMonth() {
        return referenceMonth;
    }

    @NotNull
    @Column(name = "version", nullable = false)
    @Version
    private Long version;

    protected MonthlyBalance() {
    }

    public static class Builder {
        private MonthlyBalance balance;

        private Builder() {
            balance = new MonthlyBalance();
        }

        public static Builder get(final ReferenceMonth referenceMonth) {
            if (referenceMonth == null) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));
            final Builder builder = new Builder();
            builder.balance.referenceMonth = referenceMonth;
            return builder;
        }

        public Builder withBalanceGroup(final EntityBalanceGroup group) {
            if (group == null) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));

            balance.balanceGroups.put(group.getType(), group);
            return this;
        }

        public MonthlyBalance build() {
            return balance;
        }
    }

}
