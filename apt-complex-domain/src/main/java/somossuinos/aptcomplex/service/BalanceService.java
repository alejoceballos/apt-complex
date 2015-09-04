package somossuinos.aptcomplex.service;

import somossuinos.aptcomplex.domain.balance.ApartmentBalanceGroup;
import somossuinos.aptcomplex.domain.balance.BalanceType;
import somossuinos.aptcomplex.domain.balance.EntityBalanceGroup;
import somossuinos.aptcomplex.domain.balance.MonthlyBalance;
import somossuinos.aptcomplex.domain.ReferenceMonth;
import somossuinos.aptcomplex.repository.BalanceRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.Map;

/**
 * Created by ceballos on 03/08/15.
 */
@Named
public class BalanceService {

    @Inject
    private BalanceRepository repository;

    @Transactional
    public MonthlyBalance findMonthlyBalanceByReferenceMonth(final ReferenceMonth month) {
        return repository.findWithApartmentsAndFeesByReferenceMonth(month);
    }

    @Transactional
    public ApartmentBalanceGroup findApartmentBalanceGroupByReferenceMonth(final ReferenceMonth month) {
        final MonthlyBalance balance = repository.findWithApartmentsAndFeesByReferenceMonth(month);
        final Map<BalanceType, EntityBalanceGroup> balanceGroups = balance.getBalanceGroups();

        return (ApartmentBalanceGroup) balanceGroups.get(BalanceType.INCOMES);
    }

}
