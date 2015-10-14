package somossuinos.aptcomplex.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import somossuinos.aptcomplex.domain.ReferenceMonth;
import somossuinos.aptcomplex.domain.balance.ApartmentBalanceGroup;
import somossuinos.aptcomplex.domain.balance.BalanceType;
import somossuinos.aptcomplex.domain.balance.EntityBalanceGroup;
import somossuinos.aptcomplex.domain.balance.MonthlyBalance;
import somossuinos.aptcomplex.infra.AopLogManager;
import somossuinos.aptcomplex.repository.BalanceRepository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.Map;

/**
 * @author ceballos
 * @since 03/08/15.
 */
@Named
@Singleton
public class BalanceService {

    private Log log = LogFactory.getLog(this.getClass());

    @Inject private BalanceRepository repository;

    public BalanceService() {
        if (log.isDebugEnabled()) log.debug(AopLogManager.START + AopLogManager.CONSTRUCTOR_LABEL + this.getClass().getSimpleName() + "()");
        if (log.isDebugEnabled()) log.debug(AopLogManager.END + AopLogManager.CONSTRUCTOR_LABEL + this.getClass().getSimpleName() + "()");
    }

    @PostConstruct
    public void init() {
        if (log.isDebugEnabled()) log.debug(AopLogManager.START + this.getClass().getSimpleName() + ".init()");
        if (log.isDebugEnabled()) log.debug(AopLogManager.END + this.getClass().getSimpleName() + ".init()");
    }

    @PreDestroy
    public void destroy() {
        if (log.isDebugEnabled()) log.debug(AopLogManager.START + this.getClass().getSimpleName() + ".destroy()");
        if (log.isDebugEnabled()) log.debug(AopLogManager.END + this.getClass().getSimpleName() + ".destroy()");
    }

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
