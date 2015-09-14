package somossuinos.aptcomplex.web.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import somossuinos.aptcomplex.domain.ReferenceMonth;
import somossuinos.aptcomplex.domain.statement.MonthlyStatement;
import somossuinos.aptcomplex.service.StatementService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by ceballos on 05/09/15.
 */
@Named
@Path("/statement")
public class StatementApi {

    private Log log = LogFactory.getLog(this.getClass());

    @Inject
    private StatementService service;

    @PostConstruct
    public void init() {
        log.debug(">> init()");
    }

    @GET
    @Path("/summary/year/{year}/month/{month}")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult getSummaryStatement(
            @PathParam("year") final Short year,
            @PathParam("month") final Short month) {

        log.debug(String.format(">> /statement/summary/year/%s/month/%s", year, month));

        final MonthlyStatement statement =
                service.findMonthlyStatementByReferenceMonth(
                        new ReferenceMonth(year, month));

        return ApiResult.build(ApiResultStatusType.SUCCESS).withData(statement);
    }

}
