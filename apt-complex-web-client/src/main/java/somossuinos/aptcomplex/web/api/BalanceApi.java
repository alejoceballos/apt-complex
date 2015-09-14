package somossuinos.aptcomplex.web.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import somossuinos.aptcomplex.domain.balance.ApartmentBalanceGroup;
import somossuinos.aptcomplex.domain.balance.MonthlyBalance;
import somossuinos.aptcomplex.domain.ReferenceMonth;
import somossuinos.aptcomplex.service.BalanceService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Named
@Path("/balance")
public class BalanceApi {

    private Log log = LogFactory.getLog(this.getClass());

    @Inject
    private BalanceService service;

    @PostConstruct
    public void init() {
        log.debug(">> init()");
    }

    @GET
    @Path("/summary/year/{year}/month/{month}")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult getSummaryBalance(
            @PathParam("year") final Short year,
            @PathParam("month") final Short month) {

        log.debug(String.format(">> /balance/summary/year/%s/month/%s", year, month));

        final MonthlyBalance balance =
                service.findMonthlyBalanceByReferenceMonth(
                        new ReferenceMonth(year, month));

        return ApiResult.build(ApiResultStatusType.SUCCESS).withData(balance);
    }

    @GET
    @Path("/apartment/year/{year}/month/{month}")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult getApartmentBalanceGroup(
            @PathParam("year") final Short year,
            @PathParam("month") final Short month) {

        log.debug(String.format(">> /balance/apartment/year/%s/month/%s", year, month));

        try {
            final ApartmentBalanceGroup group =
                    service.findApartmentBalanceGroupByReferenceMonth(
                            new ReferenceMonth(year, month));

            return ApiResult.build(ApiResultStatusType.SUCCESS).withData(group);

        } catch (final Exception e) {
            return ApiResult.build(ApiResultStatusType.EXCEPTION).withData(e.getMessage());
        }

    }

}
