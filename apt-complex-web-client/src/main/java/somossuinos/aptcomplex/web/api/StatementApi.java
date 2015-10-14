package somossuinos.aptcomplex.web.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import somossuinos.aptcomplex.domain.ReferenceMonth;
import somossuinos.aptcomplex.domain.statement.MonthlyStatement;
import somossuinos.aptcomplex.service.StatementService;
import somossuinos.aptcomplex.infra.AopLogManager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author ceballos
 * @since 05/09/15.
 */
@Named
@Singleton
@Path("/statement")
public class StatementApi {

    private Log log = LogFactory.getLog(this.getClass());

    @Inject
    private StatementService service;

    public StatementApi() {
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

    @GET
    @Path("/summary/year/{year}/month/{month}")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult getSummaryStatement(
            @PathParam("year") final Short year,
            @PathParam("month") final Short month) {

        if(log.isDebugEnabled()) log.debug(String.format(AopLogManager.START + "[/statement/summary/year/%s/month/%s]", year, month, year, month));

        final MonthlyStatement statement =
                service.findMonthlyStatementByReferenceMonth(
                        new ReferenceMonth(year, month));

        return ApiResult.Builder.get(ApiResultStatusType.SUCCESS).withData(statement).build();
    }

    @PUT
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResult saveStatement(final MonthlyStatement statement) {
        if(log.isDebugEnabled()) log.debug(AopLogManager.START + "[/statement/save]");

        service.save(statement);
        return ApiResult.Builder.get(ApiResultStatusType.SUCCESS).build();
    }

}
