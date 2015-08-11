package somossuinos.aptcomplex.web.infra;

import somossuinos.aptcomplex.infra.LazyCollectionNullifier;
import somossuinos.aptcomplex.web.api.ApiResult;

import javax.inject.Named;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

/**
 * @author ceballos
 * @since 2015-07-22
 */
@Named
public class LazyCollectionNullifierFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        if (containerResponseContext.getEntity() instanceof ApiResult) {
            final LazyCollectionNullifier nullifier = new LazyCollectionNullifier();
            nullifier.execute(((ApiResult) containerResponseContext.getEntity()).getData());
        }
    }
}
