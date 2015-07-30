package somossuinos.aptcomplex.domain.finance.bill;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Type;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.AbstractPersistable;
import somossuinos.aptcomplex.domain.finance.balance.Payment;
import somossuinos.aptcomplex.domain.infra.JsonJodaDateTimeSerializer;

/**
 * @author ceballos
 * @since 2015-07-23
 */
@JsonIgnoreProperties( { "new" })
@Entity
@Table(name = "bill")
@Inheritance(strategy = InheritanceType.JOINED)
public class Bill extends AbstractPersistable<Long> {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "bill_id", nullable = false)
    private Set<BillItem> items = new HashSet<>(0);

    public Set<BillItem> getItems() {
        return items;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "bill_payment",
            joinColumns = @JoinColumn(name = "bill_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "payment_id", nullable = false)
    )
    private Set<Payment> payments = new HashSet<>(0);

    public Set<Payment> getPayments() {
        return payments;
    }

    @Column(name = "reference_date", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = JsonJodaDateTimeSerializer.class)
    private DateTime referenceDate;

    public DateTime getReferenceDate() {
        return referenceDate;
    }

    @NotNull
    @Column(name = "version", nullable = false)
    @Version
    protected Long version;

    protected Bill() {
    }

    public Money totalPaid() {
        return null;
    }

    public static class Builder {
        private Bill bill;

        private Builder() {
            this.bill = new Bill();
        }

        public static Builder get() {
            return new Builder();
        }

        public Bill build() {
            return bill;
        }

        public Builder withItem(final BillItem item) {
            bill.getItems().add(item);
            return this;
        }

        public Builder withPayment(final Payment payment) {
            bill.getPayments().add(payment);
            return this;
        }

        public Builder withReferenceDay(final DateTime referenceDate) {
            bill.referenceDate = referenceDate;
            return this;
        }
    }
}
