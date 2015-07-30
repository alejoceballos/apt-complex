package somossuinos.aptcomplex.web.apartment;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import somossuinos.aptcomplex.domain.apartment.Apartment;
import somossuinos.aptcomplex.domain.apartment.ApartmentService;
import somossuinos.aptcomplex.web.api.ApiResult;
import somossuinos.aptcomplex.web.api.ApiResultStatusType;

@Named
@Path("/apartment")
public class ApartmentApiEntryPoint {

    @Inject
    private ApartmentService service;

    @PostConstruct
    public void init() {
    }

    @GET
    @Path("/year/{year}/month/{month}")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult getWithFeesByReferenceMonth(
            @PathParam("year") final int year,
            @PathParam("month") final int month) {

        final List<Apartment> apartments = service.getByReferenceMonth(year, month);

        return ApiResult.build(ApiResultStatusType.SUCCESS).withData(apartments);
    }

    @POST
    @Path("genbill/year/{year}/month/{month}")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult generateFeesForReferenceMonth(
            @PathParam("year") final int year,
            @PathParam("month") final int month) {

        final List<Apartment> apartments = service.generateFeesForReferenceMonth(year, month);

        return ApiResult.build(ApiResultStatusType.SUCCESS).withData(apartments);
    }


}
