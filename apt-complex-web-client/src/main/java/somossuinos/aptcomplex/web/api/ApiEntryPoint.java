package somossuinos.aptcomplex.web.api;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Named
@Path("transform")
public class ApiEntryPoint {

    @PostConstruct
    public void init() {
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult transform(final String from) {
        ApiResult result = null;

        return result;
    }

}
