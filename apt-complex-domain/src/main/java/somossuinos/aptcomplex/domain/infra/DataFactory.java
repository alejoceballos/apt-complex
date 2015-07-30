package somossuinos.aptcomplex.domain.infra;

import org.joda.time.DateTime;
import somossuinos.aptcomplex.domain.apartment.Apartment;
import somossuinos.aptcomplex.domain.finance.balance.Payment;
import somossuinos.aptcomplex.domain.finance.balance.TransactionType;
import somossuinos.aptcomplex.domain.finance.bill.Bill;
import somossuinos.aptcomplex.domain.finance.bill.BillItem;
import somossuinos.aptcomplex.domain.finance.bill.BillItemType;
import somossuinos.aptcomplex.domain.person.Person;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Named
public class DataFactory {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void populate() {
        for (final Apartment apartment : complete()) {
            em.persist(apartment);
        }
    }

    private Set<Apartment> complete() {
        final BigDecimal feeValue = BigDecimal.valueOf(800.00);
        final BigDecimal surchargeValue = BigDecimal.valueOf(250.00);

        final int year = 2015;
        final int firstMonth = 1;
        final int lastMonth = 8;

        final Random randomInt = new Random();

        final Set<Apartment> apartments = new HashSet<>(36);

        int aptNumber = 100;

        for (int aptCount = 0; aptCount < 36; aptCount++) {
            if (aptCount % 3 == 0) {
                aptNumber = (((aptCount / 3) + 1) * 100) + 1;
            } else {
                aptNumber++;
            }

            Apartment apartment;
            final Set<Bill> fees = new HashSet<>();

            if (aptNumber == 301) {
                for (int month = firstMonth; month <= lastMonth; month++) {
                    fees.add(Bill.Builder.get()
                            .withReferenceDay(new DateTime(year, month, 1, 0, 0))
                            .withItem(BillItem.Builder.get()
                                    .withType(BillItemType.CONDOMINIUM_FEE)
                                    .withValue(BigDecimal.valueOf(0.00))
                                    .withDescription("THE LIQUIDATOR IS EXEMPT FROM PAYMENT")
                                    .build())
                            .withItem(BillItem.Builder.get()
                                    .withType(BillItemType.CONDOMINIUM_SURCHARGE)
                                    .withValue(surchargeValue)
                                    .build())
                            .withPayment(Payment.Builder.get()
                                    .withValue(BigDecimal.valueOf(250.00))
                                    .withType(TransactionType.INCOME)
                                    .withWhen(new DateTime(year, month, 10, 0, 0))
                                    .withNote("THE LIQUIDATOR PAYS ONLY FOR SURCHARGE SINCE HE IS EXEMPT FROM CONDOMINIUM FEE")
                                    .build())
                            .build());
                }

                apartment = Apartment.Builder.get()
                        .withNumber(String.valueOf(aptNumber))
                        .withResident(Person.Builder.get().withName("JOSE NUNES").build())
                        .withFees(fees)
                        .build();

            } else if (aptNumber == 802) {
                for (int month = firstMonth; month <= lastMonth; month++) {
                    fees.add(Bill.Builder.get()
                            .withReferenceDay(new DateTime(year, month, 1, 0, 0))
                            .withItem(BillItem.Builder.get()
                                    .withType(BillItemType.CONDOMINIUM_FEE)
                                    .withValue(feeValue)
                                    .build())
                            .withItem(BillItem.Builder.get()
                                    .withType(BillItemType.CONDOMINIUM_SURCHARGE)
                                    .withValue(surchargeValue)
                                    .build())
                            .withItem(BillItem.Builder.get()
                                    .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                    .withValue(BigDecimal.valueOf(-50.00))
                                    .withDescription("R$ 50.00 DISCOUNT FOR PAYMENT BEFORE UNTIL 10")
                                    .build())
                            .withPayment(Payment.Builder.get()
                                    .withValue(BigDecimal.valueOf(1000.00))
                                    .withType(TransactionType.INCOME)
                                    .withWhen(new DateTime(year, month, 10, 0, 0))
                                    .withNote("R$ 50.00 DISCOUNT FOR PAYMENT BEFORE UNTIL 10")
                                    .build())
                            .build());
                }

                apartment = Apartment.Builder.get()
                        .withNumber(String.valueOf(aptNumber))
                        .withResident(Person.Builder.get().withName("ALEJO CEBALLOS").build())
                        .withFees(fees)
                        .build();

            } else {
                for (int month = firstMonth; month <= lastMonth; month++) {
                    final Bill.Builder fee =
                            Bill.Builder.get()
                                    .withReferenceDay(new DateTime(year, month, 1, 0, 0))
                                    .withItem(BillItem.Builder.get()
                                            .withType(BillItemType.CONDOMINIUM_FEE)
                                            .withValue(feeValue)
                                            .build())
                                    .withItem(BillItem.Builder.get()
                                            .withType(BillItemType.CONDOMINIUM_SURCHARGE)
                                            .withValue(surchargeValue)
                                            .build());

                    final int random = randomInt.nextInt(3);

                    if (random > 0) {
                        BigDecimal paymentVal;

                        if (random == 1) {
                            paymentVal = BigDecimal.valueOf(950.00);
                        } else {
                            paymentVal = BigDecimal.valueOf(1050.00);
                        }

                        fee.withPayment(Payment.Builder.get()
                                .withValue(paymentVal)
                                .withType(TransactionType.INCOME)
                                .withWhen(new DateTime(year, month, 11, 0, 0))
                                .withNote("DO NOT KNOW THE EXACT DATE OF PAYMENT. WILL ASSUME AFTER DAY 10, SO NO DISCOUNT")
                                .build());
                    }

                    fees.add(fee.build());
                }

                apartment = Apartment.Builder.get()
                        .withNumber(String.valueOf(aptNumber))
                        .withResident(Person.Builder.get().withName("JOHN " + aptCount + " DOE").build())
                        .withFees(fees)
                        .build();
            }

            apartments.add(apartment);
        }

        return apartments;
    }

}
