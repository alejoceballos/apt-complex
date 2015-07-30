package somossuinos.aptcomplex.domain.finance.bill;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.joda.money.Money;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.math.BigDecimal;

/**
 * @author ceballos
 * @since 2015-07-23
 */
@Entity
@Table(name = "bill_item")
public class BillItem extends AbstractPersistable<Long> {

    @NotNull
    @Column(name = "type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private BillItemType type;

    public BillItemType getType() {
        return type;
    }

    @Column(name = "description", length = 255)
    private String description;

    public String getDescription() {
        return description;
    }

    @NotNull
    @Column(name = "value", nullable = false)
    private BigDecimal value;

    public BigDecimal getValue() {
        return value;
    }

    @NotNull
    @Column(name = "version", nullable = false)
    @Version
    private Long version;

    protected BillItem() {
    }

    public static class Builder {
        private BillItem item;

        private Builder() {
            item = new BillItem();
        }

        public static Builder get() {
            return new Builder();
        }

        public BillItem build() {
            return item;
        }

        public Builder withType(final BillItemType type) {
            item.type = type;
            return this;
        }

        public Builder withDescription(final String description) {
            item.description = description;
            return this;
        }

        public Builder withValue(final BigDecimal value) {
            item.value = value;
            return this;
        }
    }
}
