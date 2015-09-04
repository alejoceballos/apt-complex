package somossuinos.aptcomplex.domain.bill;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.AbstractPersistable;
import somossuinos.aptcomplex.domain.exception.AptComplexDomainException;
import somossuinos.aptcomplex.domain.exception.ExceptionMessages;
import somossuinos.aptcomplex.domain.payment.Payment;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ceballos
 * @since 2015-07-23
 */
@JsonIgnoreProperties( { "new" } )
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "bill")
public class Bill extends AbstractPersistable<Long> {

    @NotNull
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

    @NotNull
    @Version
    @Column(name = "version", nullable = false)
    protected Long version;

    protected Bill() {
    }

    public BigDecimal totalFee() {
        BigDecimal total = BigDecimal.ZERO;

        for (final BillItem item : items) {
            total = total.add(item.getValue());
        }

        return total;
    }

    public BigDecimal totalPayment() {
        BigDecimal total = BigDecimal.ZERO;

        for (final Payment payment: payments) {
            total = total.add(payment.getValue());
        }

        return total;
    }

    public static class Builder {
        protected Bill bill;

        protected Builder() {
            this.bill = new Bill();
        }

        public static Builder get() {
            return new Builder();
        }

        public static Builder get(final Bill bill) {
            final Builder builder = new Builder();

            for (final BillItem item : bill.getItems()) {
                builder.bill.items.add(BillItem.Builder.get(item).build());
            }

            for (final Payment pymnt : bill.getPayments()) {
                builder.bill.payments.add(Payment.Builder.get(pymnt).build());
            }

            return builder;
        }

        public Bill build() {
            return bill;
        }

        public Builder withItem(final BillItem item) {
            if (item == null) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));
            bill.getItems().add(item);
            return this;
        }

        public Builder withPayment(final Payment payment) {
            if (payment == null) throw new AptComplexDomainException(new IllegalArgumentException(ExceptionMessages.PARAMETER_CANNOT_BE_NULL));
            bill.getPayments().add(payment);
            return this;
        }

    }
}
