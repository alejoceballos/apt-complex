package somossuinos.aptcomplex.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import somossuinos.aptcomplex.domain.ReferenceMonth;
import somossuinos.aptcomplex.domain.statement.MonthlyStatement;
import somossuinos.aptcomplex.infra.AopLogManager;
import somossuinos.aptcomplex.repository.StatementRepository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.transaction.Transactional;

/**
 * @author ceballos
 * @since 03/09/15.
 */
@Named
@Singleton
public class StatementService {

    private Log log = LogFactory.getLog(this.getClass());

    public StatementService() {
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

    @Inject
    private StatementRepository repository;

    @Transactional
    public MonthlyStatement findMonthlyStatementByReferenceMonth(final ReferenceMonth month) {
        return repository.findWithApartmentsByReferenceMonth(month);
    }

    @Transactional
    public void save(final MonthlyStatement statement) {
        repository.save(statement);
    }

}
