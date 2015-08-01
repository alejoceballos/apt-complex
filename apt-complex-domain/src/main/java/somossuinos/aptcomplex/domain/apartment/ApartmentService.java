package somossuinos.aptcomplex.domain.apartment;

import org.joda.time.DateTime;
import somossuinos.aptcomplex.domain.finance.bill.Bill;
import somossuinos.aptcomplex.domain.finance.bill.BillItem;
import somossuinos.aptcomplex.domain.finance.bill.BillItemType;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @Transactional
    public Apartment updateFeeForReferenceMonth(
            final Long id,
            final int year,
            final int month,
            final ApartmentOperationDto dto) {

        final DateTime iniDate = new DateTime(year, month, 1, 0, 0);
        final DateTime endDate = new DateTime(year, month, iniDate.toGregorianCalendar().getActualMaximum(Calendar.DAY_OF_MONTH), 0, 0);
        final Apartment apartment = repository.findOneByReferenceMonth(id, iniDate, endDate);

        Bill mainFee = null;

        for (final Bill fee : apartment.getFees()) {
            final Set<BillItem> itemsToRemove = new HashSet<>();

            for (BillItem item : fee.getItems()) {
                boolean found = false;

                for (final Long removed : dto.getRemovedItems()) {
                    if (removed.equals(item.getId())) {
                        itemsToRemove.add(item);
                        dto.getRemovedItems().remove(removed);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    // Update
                    for (final BillItem updated : dto.getUpdatedItems()) {
                        if (item.getId().equals(updated.getId())) {
                            BillItem.Builder.get(item)
                                    .withDescription(updated.getDescription())
                                    .withType(updated.getType())
                                    .withValue(updated.getValue())
                                    .build();
                            dto.getUpdatedItems().remove(updated);
                            break;
                        }
                    }
                }
            }

            // Remove
            if (itemsToRemove.size() > 0) {
                fee.getItems().removeAll(itemsToRemove);
            }

            mainFee = fee;
        }

        // Add
        for (final BillItem newItem : dto.getNewItems()) {
            mainFee.getItems().add(newItem);
        }

        return apartment;
    }

}
