package somossuinos.aptcomplex.service;

import somossuinos.aptcomplex.domain.ReferenceMonth;
import somossuinos.aptcomplex.domain.statement.MonthlyStatement;
import somossuinos.aptcomplex.repository.StatementRepository;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by ceballos on 03/09/15.
 */
@Named
public class StatementService {

    @Inject
    private StatementRepository repository;

    public MonthlyStatement findMonthlyStatementByReferenceMonth(final ReferenceMonth month) {
        return repository.findWithApartmentsByReferenceMonth(month);
    }

}
