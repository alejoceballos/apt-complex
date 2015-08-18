package somossuinos.aptcomplex.domain.bill;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.AbstractPersistable;
import somossuinos.aptcomplex.domain.exception.AptComplexDomainException;
import somossuinos.aptcomplex.domain.exception.ExceptionMessages;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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
        private Map<String, Object> attributes = new HashMap<>();

        private Builder() {
            item = new BillItem();
        }

        public static Builder get() {
            return new Builder();
        }

        public static Builder get(final BillItem item) {
            final Builder builder = new Builder();
            builder.item.description = item.description;
            builder.item.type = item.type;
            builder.item.value = item.value;

            return builder;
        }

        public BillItem build() {
            for (final Map.Entry<String, Object> entry : attributes.entrySet()) {
                try {
                    final Field field = BillItem.class.getDeclaredField(entry.getKey());
                    field.setAccessible(true);
                    field.set(item, entry.getValue());

                } catch (final ReflectiveOperationException e) {
                    throw new AptComplexDomainException(e);
                }
            }

            return item;
        }

        public Builder withType(final BillItemType type) {
            if (type == null) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));
            attributes.put("type", type);
            return this;
        }

        public Builder withDescription(final String description) {
            if (StringUtils.isBlank(description)) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));
            attributes.put("description", description);
            return this;
        }

        public Builder withValue(final BigDecimal value) {
            if (value == null) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));
            attributes.put("value", value);
            return this;
        }
    }

}
