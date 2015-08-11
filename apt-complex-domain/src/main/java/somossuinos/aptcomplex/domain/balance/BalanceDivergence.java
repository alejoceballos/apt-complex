package somossuinos.aptcomplex.domain.balance;

import org.springframework.data.jpa.domain.AbstractPersistable;
import somossuinos.aptcomplex.domain.exception.AptComplexDomainException;
import somossuinos.aptcomplex.domain.exception.ExceptionMessages;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Version;
import java.math.BigDecimal;

/**
 * @author ceballos
 * @since 2015-07-26
 */
@Entity
@Table(name = "balance_divergence")
public class BalanceDivergence extends AbstractPersistable<Long> {

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private DivergenceType type;

    public DivergenceType getType() {
        return type;
    }

    @Column(name = "value",  nullable = false, precision = 15, scale = 2)
    private BigDecimal value;

    public BigDecimal getValue() {
        return value;
    }

    @Column(name = "description", nullable = false, length = 4000)
    private String description;

    public String getDescription() {
        return description;
    }

    @Column(name = "version", nullable = false)
    @Version
    private Long version;

    protected BalanceDivergence() {
    }

    public static class Builder {
        private BalanceDivergence divergence;

        private Builder() {
            divergence = new BalanceDivergence();
        }

        public static Builder get(final DivergenceType type) {
            if (type == null) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));
            final Builder builder = new Builder();
            builder.divergence.type = type;
            return builder;
        }

        public Builder withValue(final BigDecimal value) {
            if (value == null) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));
            divergence.value = value;
            return this;
        }

        public Builder withDescription(final String description) {
            if (description == null) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));
            divergence.description = description;
            return this;
        }

        public BalanceDivergence build() {
            return divergence;
        }

    }
}
