package somossuinos.aptcomplex.web;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import somossuinos.aptcomplex.web.api.BalanceApi;
import somossuinos.aptcomplex.web.api.StatementApi;
import somossuinos.aptcomplex.web.infra.LazyCollectionNullifierFilter;

import javax.ws.rs.ApplicationPath;

/**
 * @author ceballos
 * @since 2015-07-19
 */
@Configuration
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(LazyCollectionNullifierFilter.class);
        register(BalanceApi.class);
        register(StatementApi.class);
    }

}
