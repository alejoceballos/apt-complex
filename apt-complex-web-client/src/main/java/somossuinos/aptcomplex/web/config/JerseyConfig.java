package somossuinos.aptcomplex.web.config;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import somossuinos.aptcomplex.web.apartment.ApartmentApiEntryPoint;

@Configuration
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(ApartmentApiEntryPoint.class);
    }

}
