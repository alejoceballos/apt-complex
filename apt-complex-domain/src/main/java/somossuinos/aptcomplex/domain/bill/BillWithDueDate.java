package somossuinos.aptcomplex.domain.bill;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import somossuinos.aptcomplex.domain.exception.AptComplexDomainException;
import somossuinos.aptcomplex.domain.exception.ExceptionMessages;
import somossuinos.aptcomplex.domain.payment.Payment;
import somossuinos.aptcomplex.infra.JsonJodaDateTimeDeserializer;
import somossuinos.aptcomplex.infra.JsonJodaDateTimeSerializer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author ceballos
 * @since 2015-08-06
 */
@Entity
@Table(name = "bill_with_due_date")
public class BillWithDueDate extends Bill {

    @NotNull
    @Column(name = "due_date", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = JsonJodaDateTimeSerializer.class)
    @JsonDeserialize(using = JsonJodaDateTimeDeserializer.class)
    private DateTime dueDate;

    public DateTime getDueDate() {
        return dueDate;
    }

    protected BillWithDueDate() {
    }

    public static class Builder extends Bill.Builder {

        private Builder() {
            super.bill = new BillWithDueDate();
        }

        public static Builder get() {
            return new Builder();
        }

        public Builder withDueDate(final DateTime dueDate) {
            if (dueDate == null) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));
            ((BillWithDueDate) bill).dueDate = dueDate;
            return this;
        }

        public Builder withItem(final BillItem item) {
            super.withItem(item);
            return this;
        }

        public Builder withPayment(final Payment payment) {
            super.withPayment(payment);
            return this;
        }

        public BillWithDueDate build() {
            return (BillWithDueDate) bill;
        }

    }

}
