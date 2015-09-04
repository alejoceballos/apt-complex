package somossuinos.aptcomplex.domain.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.AbstractPersistable;
import somossuinos.aptcomplex.domain.exception.AptComplexDomainException;
import somossuinos.aptcomplex.domain.exception.ExceptionMessages;
import somossuinos.aptcomplex.infra.JsonJodaDateTimeDeserializer;
import somossuinos.aptcomplex.infra.JsonJodaDateTimeSerializer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.lang.reflect.Field;
import java.math.BigDecimal;
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

    @Size(max = 255)
    @Column(name = "note", length = 255)
    private String note;

    public String getNote() {
        return note;
    }

    @NotNull
    @Version
    @Column(name = "version", nullable = false)
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

        public static Builder get(final Payment payment) {
            final Builder builder = new Builder();
            builder.payment.note = payment.note;
            builder.payment.type = payment.type;
            builder.payment.value = payment.value;
            builder.payment.when = payment.when;

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
            if (value == null) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));
            attributes.put("value", value);
            return this;
        }

        public Builder withType(final TransactionType type) {
            if (type == null) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));
            attributes.put("type", type);
            return this;
        }

        public Builder withWhen(final DateTime when) {
            if (when == null) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));
            attributes.put("when", when);
            return this;
        }

        public Builder withNote(final String note) {
            if (StringUtils.isBlank(note)) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));
            attributes.put("note", note);
            return this;
        }
    }

}
