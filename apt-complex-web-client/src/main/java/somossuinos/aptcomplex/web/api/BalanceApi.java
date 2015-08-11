package somossuinos.aptcomplex.web.api;

import somossuinos.aptcomplex.domain.balance.ApartmentBalanceGroup;
import somossuinos.aptcomplex.domain.balance.MonthlyBalance;
import somossuinos.aptcomplex.domain.balance.ReferenceMonth;
import somossuinos.aptcomplex.domain.bill.BillItem;
import somossuinos.aptcomplex.domain.payment.Payment;
import somossuinos.aptcomplex.service.ApartmentOperationDTO;
import somossuinos.aptcomplex.service.BalanceService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Named
@Path("/balance")
public class BalanceApi {

    @Inject
    private BalanceService service;

    @PostConstruct
    public void init() {
    }

    @GET
    @Path("/summary/year/{year}/month/{month}")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult getSummaryBalance(
            @PathParam("year") final Short year,
            @PathParam("month") final Short month) {

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

        final ApartmentBalanceGroup group =
                service.findApartmentBalanceGroupByReferenceMonth(
                        new ReferenceMonth(year, month));

        return ApiResult.build(ApiResultStatusType.SUCCESS).withData(group);
    }

    @POST
    @Path("generate-bill/year/{year}/month/{month}")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult generateFeesForReferenceMonth(
            @PathParam("year") final Integer year,
            @PathParam("month") final Integer month) {

//        service.generateFeesForReferenceMonth(year, month);
//        final List<Apartment> apartments = service.getByReferenceMonth(year, month);
//
//        return ApiResult.build(ApiResultStatusType.SUCCESS).withData(apartments);

        return null;
    }

    @POST
    @Path("update-bill/id/{id}/year/{year}/month/{month}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult updateFeeForReferenceMonth(
            @PathParam("id") final Long id,
            @PathParam("year") final Integer year,
            @PathParam("month") final Integer month,
            final ApartmentOperationDTO<BillItem> data) {

//        final Apartment apartment = service.updateFeeForReferenceMonth(id, year, month, data);
//
//        return ApiResult.build(ApiResultStatusType.SUCCESS).withData(apartment);

        return null;
    }

    @POST
    @Path("update-payments/id/{id}/year/{year}/month/{month}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult updatePaymentsForReferenceMonth(
            @PathParam("id") final Long id,
            @PathParam("year") final Integer year,
            @PathParam("month") final Integer month,
            final ApartmentOperationDTO<Payment> data) {

//        final Apartment apartment = service.updatePaymentsForReferenceMonth(id, year, month, data);
//
//        return ApiResult.build(ApiResultStatusType.SUCCESS).withData(apartment);

        return null;
    }

    @POST
    @Path("generate-payment/year/{year}/month/{month}")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult generatePaymentsForReferenceMonth(
            @PathParam("year") final Integer year,
            @PathParam("month") final Integer month) {

//        service.generatePaymentsForReferenceMonth(year, month);
//        final List<Apartment> apartments = service.getByReferenceMonth(year, month);
//
//        return ApiResult.build(ApiResultStatusType.SUCCESS).withData(apartments);

        return null;
    }
}
