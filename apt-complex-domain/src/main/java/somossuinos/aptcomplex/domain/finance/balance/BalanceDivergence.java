package somossuinos.aptcomplex.domain.finance.balance;

import org.hibernate.annotations.Type;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

/**
 * @author ceballos
 * @since 2015-07-26
 */
@Entity
@Table(name = "balance_divergence")
public class BalanceDivergence extends AbstractPersistable<Long> {

    @Column(name = "value",  nullable = false, precision = 15, scale = 2)
    private Money value;

    public Money getValue() {
        return value;
    }

    @Column(name = "type", nullable = false)
    private TransactionType type;

    public TransactionType getType() {
        return type;
    }

    @Column(name = "reference_date", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime referenceDate;

    public DateTime getReferenceDate() {
        return referenceDate;
    }

    @Column(name = "version", nullable = false)
    @Version
    private Long version;

    protected BalanceDivergence() {
    }
}
