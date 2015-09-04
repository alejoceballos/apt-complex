package somossuinos.aptcomplex.domain.statement;

import org.apache.commons.collections.CollectionUtils;
import somossuinos.aptcomplex.domain.balance.BalanceType;
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
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by ceballos on 31/08/15.
 */
@Entity
@DiscriminatorValue("INCOMES")
@Table(name = "apartment_statement_group")
public class ApartmentStatementGroup extends EntityStatementGroup {

    @NotNull
    @Column(name = "total_fee", nullable = false)
    private BigDecimal totalFee = BigDecimal.ZERO;

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    @NotNull
    @Column(name = "total_surcharge", nullable = false)
    private BigDecimal totalSurcharge = BigDecimal.ZERO;

    public BigDecimal getTotalSurcharge() {
        return totalSurcharge;
    }

    @NotNull
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "apartment_statement_group_id", nullable = false)
    @MapKeyColumn(name = "apartment_number")
    private Map<String, ApartmentStatement> apartmentsStatement = new HashMap<>(0);

    public Map<String, ApartmentStatement> getApartmentsStatements() {
        return apartmentsStatement;
    }

    protected ApartmentStatementGroup() { }

    protected ApartmentStatementGroup(final BalanceType type) {
        super(type);
    }

    public static class Builder extends EntityStatementGroup.Builder {

        public Builder() {
            super.group = new ApartmentStatementGroup(BalanceType.INCOMES);
        }

        public static Builder get() {
            return new Builder();
        }

        public Builder withTotalFee(final BigDecimal totalFee) {
            if (totalFee == null) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));
            ((ApartmentStatementGroup) group).totalFee = totalFee;
            return this;
        }

        public Builder withTotalSurcharge(final BigDecimal totalSurcharge) {
            if (totalSurcharge == null) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));
            ((ApartmentStatementGroup) group).totalSurcharge = totalSurcharge;
            return this;
        }

        public Builder withApartmentStatement(final ApartmentStatement statement) {
            if (statement == null) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));
            ((ApartmentStatementGroup) group).apartmentsStatement.put(statement.getApartment().getNumber(), statement);
            return this;
        }

        public Builder withApartmentStatements(final Set<ApartmentStatement> statements) {
            if (CollectionUtils.isEmpty(statements)) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));
            for (final ApartmentStatement statement : statements) {
                ((ApartmentStatementGroup) group).apartmentsStatement.put(statement.getApartment().getNumber(), statement);
            }
            return this;
        }

        public ApartmentStatementGroup build() {
            return (ApartmentStatementGroup) group;
        }

    }
}
