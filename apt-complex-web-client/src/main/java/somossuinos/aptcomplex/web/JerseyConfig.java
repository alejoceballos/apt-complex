package somossuinos.aptcomplex.web;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import somossuinos.aptcomplex.web.apartment.ApartmentApiEntryPoint;
import somossuinos.aptcomplex.web.infra.LazyCollectionNullifierFilter;

/**
 * @author ceballos
 * @since 2015-07-19
 */
@Configuration
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(LazyCollectionNullifierFilter.class);
        register(ApartmentApiEntryPoint.class);
    }

}
