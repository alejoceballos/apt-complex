package somossuinos.aptcomplex.web.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import somossuinos.aptcomplex.domain.ReferenceMonth;
import somossuinos.aptcomplex.domain.balance.ApartmentBalanceGroup;
import somossuinos.aptcomplex.domain.balance.MonthlyBalance;
import somossuinos.aptcomplex.service.BalanceService;
import somossuinos.aptcomplex.infra.AopLogManager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Named
@Singleton
@Path("/balance")
public class BalanceApi {

    private Log log = LogFactory.getLog(this.getClass());

    @Inject private BalanceService service;

    public BalanceApi() {
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
    public ApiResult getSummaryBalance(
            @PathParam("year") final Short year,
            @PathParam("month") final Short month) {

        if(log.isDebugEnabled()) log.debug(String.format(AopLogManager.START + "[/balance/summary/year/%s/month/%s]", year, month, year, month));

        try {
            final MonthlyBalance balance =
                    service.findMonthlyBalanceByReferenceMonth(
                            new ReferenceMonth(year, month));

            return ApiResult.Builder.get(ApiResultStatusType.SUCCESS).withData(balance).build();

        } catch (final Exception e) {
            return ApiResult.Builder.get(ApiResultStatusType.EXCEPTION).withData(e.getMessage()).build();
        }
    }

    @GET
    @Path("/apartment/year/{year}/month/{month}")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult getApartmentBalanceGroup(
            @PathParam("year") final Short year,
            @PathParam("month") final Short month) {

        if(log.isDebugEnabled()) log.debug(String.format(AopLogManager.START + "[/balance/apartment/year/%s/month/%s]", year, month));

        try {
            final ApartmentBalanceGroup group =
                    service.findApartmentBalanceGroupByReferenceMonth(
                            new ReferenceMonth(year, month));

            return ApiResult.Builder.get(ApiResultStatusType.SUCCESS).withData(group).build();

        } catch (final Exception e) {
            return ApiResult.Builder.get(ApiResultStatusType.EXCEPTION).withData(e.getMessage()).build();
        }

    }

}
