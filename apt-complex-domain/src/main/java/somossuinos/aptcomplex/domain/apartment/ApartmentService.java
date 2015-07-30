package somossuinos.aptcomplex.domain.apartment;

import org.joda.time.DateTime;
import somossuinos.aptcomplex.domain.finance.bill.Bill;
import somossuinos.aptcomplex.domain.finance.bill.BillItem;
import somossuinos.aptcomplex.domain.finance.bill.BillItemType;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

/**
 * @author ceballos
 * @since 2015-07-21
 */
@Named
public class ApartmentService {

    @Inject
    private ApartmentRepository repository;

    @Transactional
    public List<Apartment> getByReferenceMonth(final int year, final int month) {
        final DateTime iniDate = new DateTime(year, month, 1, 0, 0);
        final DateTime endDate = new DateTime(year, month, iniDate.toGregorianCalendar().getActualMaximum(Calendar.DAY_OF_MONTH), 0, 0);

        return repository.findByFeePeriod(iniDate, endDate);
    }

    @Transactional
    public List<Apartment> generateFeesForReferenceMonth(final int year, final int month) {
        final BigDecimal feeValue = BigDecimal.valueOf(800);
        final BigDecimal surchargeValue = BigDecimal.valueOf(250);
        final BigDecimal discountValue = BigDecimal.valueOf(-50);

        final List<Apartment> apartments = repository.findAll();

        for (final Apartment apartment : apartments) {
            apartment.getFees().add(
                    Bill.Builder.get()
                            .withReferenceDay(new DateTime(year, month, 1, 0, 0))
                            .withItem(
                                    BillItem.Builder.get()
                                            .withType(BillItemType.CONDOMINIUM_FEE)
                                            .withValue(feeValue)
                                            .build())
                            .withItem(
                                    BillItem.Builder.get()
                                            .withType(BillItemType.CONDOMINIUM_SURCHARGE)
                                            .withValue(surchargeValue)
                                            .build())
                            .withItem(
                                    BillItem.Builder.get()
                                            .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                            .withValue(discountValue)
                                            .withDescription("R$ 50.00 DISCOUNT FOR PAYMENT UNTIL DAY 10")
                                            .build())
                            .build());
        }

        final DateTime iniDate = new DateTime(year, month, 1, 0, 0);
        final DateTime endDate = new DateTime(year, month, iniDate.toGregorianCalendar().getActualMaximum(Calendar.DAY_OF_MONTH), 0, 0);

        return repository.findByFeePeriod(iniDate, endDate);
    }

}
