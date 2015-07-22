package somossuinos.aptcomplex.web.apartment;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import somossuinos.aptcomplex.domain.apartment.Apartment;
import somossuinos.aptcomplex.domain.apartment.ApartmentService;
import somossuinos.aptcomplex.web.api.ApiResult;
import somossuinos.aptcomplex.web.api.ApiResultStatusType;

@Named
@Path("apartment")
public class ApartmentApiEntryPoint {

    @Inject
    private ApartmentService service;

    @PostConstruct
    public void init() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult get() {
        final List<Apartment> apartments = service.getAll();

        return ApiResult.build(ApiResultStatusType.SUCCESS).withData(apartments);
    }

}
