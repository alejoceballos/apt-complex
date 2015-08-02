package somossuinos.aptcomplex.web.apartment;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import somossuinos.aptcomplex.domain.apartment.Apartment;
import somossuinos.aptcomplex.domain.apartment.ApartmentOperationDto;
import somossuinos.aptcomplex.domain.apartment.ApartmentService;
import somossuinos.aptcomplex.domain.finance.balance.Payment;
import somossuinos.aptcomplex.domain.finance.bill.BillItem;
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
            @PathParam("year") final Integer year,
            @PathParam("month") final Integer month) {

        final List<Apartment> apartments = service.getByReferenceMonth(year, month);

        return ApiResult.build(ApiResultStatusType.SUCCESS).withData(apartments);
    }

    @POST
    @Path("generate-bill/year/{year}/month/{month}")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult generateFeesForReferenceMonth(
            @PathParam("year") final Integer year,
            @PathParam("month") final Integer month) {

        service.generateFeesForReferenceMonth(year, month);
        final List<Apartment> apartments = service.getByReferenceMonth(year, month);

        return ApiResult.build(ApiResultStatusType.SUCCESS).withData(apartments);
    }

    @POST
    @Path("update-bill/id/{id}/year/{year}/month/{month}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult updateFeeForReferenceMonth(
            @PathParam("id") final Long id,
            @PathParam("year") final Integer year,
            @PathParam("month") final Integer month,
            final ApartmentOperationDto<BillItem> data) {

        final Apartment apartment = service.updateFeeForReferenceMonth(id, year, month, data);

        return ApiResult.build(ApiResultStatusType.SUCCESS).withData(apartment);
    }

    @POST
    @Path("update-payments/id/{id}/year/{year}/month/{month}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult updatePaymentsForReferenceMonth(
            @PathParam("id") final Long id,
            @PathParam("year") final Integer year,
            @PathParam("month") final Integer month,
            final ApartmentOperationDto<Payment> data) {

        final Apartment apartment = service.updatePaymentsForReferenceMonth(id, year, month, data);

        return ApiResult.build(ApiResultStatusType.SUCCESS).withData(apartment);
    }

    @POST
    @Path("generate-payment/year/{year}/month/{month}")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult generatePaymentsForReferenceMonth(
            @PathParam("year") final Integer year,
            @PathParam("month") final Integer month) {

        service.generatePaymentsForReferenceMonth(year, month);
        final List<Apartment> apartments = service.getByReferenceMonth(year, month);

        return ApiResult.build(ApiResultStatusType.SUCCESS).withData(apartments);
    }
}
