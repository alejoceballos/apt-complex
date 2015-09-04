package somossuinos.aptcomplex.domain.statement;

import org.springframework.data.jpa.domain.AbstractPersistable;
import somossuinos.aptcomplex.domain.balance.BalanceType;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/**
 * @author ceballos
 * @since 2015-08-31
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
@Table(name = "entity_statement_group")
public abstract class EntityStatementGroup extends AbstractPersistable<Long> {

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

    protected EntityStatementGroup() {
    }

    protected EntityStatementGroup(final BalanceType type) {
        this.type = type;
    }

    protected static class Builder {
        protected EntityStatementGroup group;
    }

}
