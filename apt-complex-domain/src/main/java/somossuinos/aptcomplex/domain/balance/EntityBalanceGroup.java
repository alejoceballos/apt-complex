package somossuinos.aptcomplex.domain.balance;

import org.springframework.data.jpa.domain.AbstractPersistable;
import somossuinos.aptcomplex.domain.exception.AptComplexDomainException;
import somossuinos.aptcomplex.domain.exception.ExceptionMessages;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ceballos
 * @since 2015-08-04
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
@Table(name = "entity_balance_group")
public abstract class EntityBalanceGroup extends AbstractPersistable<Long> {

    @NotNull
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "entity_balance_group_id", nullable = false)
    protected Set<BalanceDivergence> divergences = new HashSet<>(0);

    public Set<BalanceDivergence> getDivergences() {
        return divergences;
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, insertable = false, updatable = false)
    protected BalanceType type;

    public BalanceType getType() {
        return type;
    }

    @NotNull
    @Version
    @Column(name = "version", nullable = false)
    protected Long version;

    protected EntityBalanceGroup() {
    }

    protected EntityBalanceGroup(final BalanceType type) {
        this.type = type;
    }

    protected static class Builder {
        protected EntityBalanceGroup group;

        public Builder withDivergence(final BalanceDivergence divergence) {
            if (divergence == null) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));
            group.divergences.add(divergence);
            return this;
        }

    }
}
