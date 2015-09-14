package somossuinos.aptcomplex.domain.statement;

import org.springframework.data.jpa.domain.AbstractPersistable;
import somossuinos.aptcomplex.domain.balance.BalanceType;
import somossuinos.aptcomplex.domain.ReferenceMonth;
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
 * A statement is the balance summary presented to each apartment owner / responsible
 * every month so it can keep up with condominium accounts.
 * <p>
 *     Differently from monthly balance, it presents only totals of each type of
 *     income and expense.
 * </p>
 *
 * @author ceballos
 * @since 2015-08-31
 */
@Entity
@Table(name = "monthly_statement")
public class MonthlyStatement extends AbstractPersistable<Long> {

    @NotNull
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "monthly_statement_id", nullable = false)
    @MapKeyColumn(name = "type")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<BalanceType, EntityStatementGroup> statementGroups = new HashMap<>();

    public Map<BalanceType, EntityStatementGroup> getStatementGroups() {
        return statementGroups;
    }

    @NotNull
    @Embedded
    private ReferenceMonth referenceMonth;

    public ReferenceMonth getReferenceMonth() {
        return referenceMonth;
    }

    @NotNull
    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    protected MonthlyStatement() {
    }

    public static class Builder {

        private MonthlyStatement statement;

        private Builder() {
            statement = new MonthlyStatement();
        }

        public static Builder get(final ReferenceMonth referenceMonth) {
            if (referenceMonth == null) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));
            final Builder builder = new Builder();
            builder.statement.referenceMonth = referenceMonth;
            return builder;
        }

        public Builder withStatementGroup(final EntityStatementGroup group) {
            if (group == null) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));
            statement.statementGroups.put(group.getType(), group);
            return this;
        }

        public MonthlyStatement build() {
            return statement;
        }
    }

}
