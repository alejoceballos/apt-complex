package somossuinos.aptcomplex.domain.finance.balance;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Type;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.AbstractPersistable;
import somossuinos.aptcomplex.domain.exception.AptComplexDomainException;
import somossuinos.aptcomplex.domain.finance.balance.TransactionType;
import somossuinos.aptcomplex.domain.finance.bill.BillItem;
import somossuinos.aptcomplex.domain.infra.JsonJodaDateTimeDeserializer;
import somossuinos.aptcomplex.domain.infra.JsonJodaDateTimeSerializer;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.AbstractCollection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ceballos
 * @since 2015-07-20
 */
@JsonIgnoreProperties( { "new" })
@Entity
@Table(name = "payment")
public class Payment extends AbstractPersistable<Long> {

    @NotNull
    @Column(name = "value", nullable = false, precision = 15, scale = 2)
    private BigDecimal value;

    public BigDecimal getValue() {
        return value;
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 50)
    private TransactionType type;

    public TransactionType getType() {
        return type;
    }

    @NotNull
    @Column(name = "payment_date", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = JsonJodaDateTimeSerializer.class)
    @JsonDeserialize(using = JsonJodaDateTimeDeserializer.class)
    private DateTime when;

    public DateTime getWhen() {
        return when;
    }

    @Column(name = "note", length = 255)
    private String note;

    public String getNote() {
        return note;
    }

    @Column(name = "version", nullable = false)
    @Version
    private Long version;

    protected Payment() {
    }

    public static class Builder {
        private Payment payment;
        private Map<String, Object> attributes = new HashMap<>();

        private Builder() {
            this.payment = new Payment();
        }

        public static Builder get() {
            return new Builder();
        }

        public static Builder get(final Payment item) {
            final Builder builder = new Builder();
            builder.payment = item;

            return builder;
        }

        public Payment build() {
            for (final Map.Entry<String, Object> entry : attributes.entrySet()) {
                try {
                    final Field field = Payment.class.getDeclaredField(entry.getKey());
                    field.setAccessible(true);
                    field.set(payment, entry.getValue());

                } catch (final ReflectiveOperationException e) {
                    throw new AptComplexDomainException(e);
                }
            }

            return payment;
        }

        public Builder withValue(final BigDecimal value) {
            attributes.put("value", value);
            return this;
        }

        public Builder withType(final TransactionType type) {
            attributes.put("type", type);
            return this;
        }

        public Builder withWhen(final DateTime when) {
            attributes.put("when", when);
            return this;
        }

        public Builder withNote(final String note) {
            attributes.put("note", note);
            return this;
        }
    }

}
