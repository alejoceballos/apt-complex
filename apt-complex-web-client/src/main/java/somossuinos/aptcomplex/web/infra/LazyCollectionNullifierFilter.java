package somossuinos.aptcomplex.web.infra;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import somossuinos.aptcomplex.infra.AopLogManager;
import somossuinos.aptcomplex.infra.LazyCollectionNullifier;
import somossuinos.aptcomplex.web.api.ApiResult;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

/**
 * @author ceballos
 * @since 2015-07-22
 */
@Named
@Singleton
public class LazyCollectionNullifierFilter implements ContainerResponseFilter {

    private Log log = LogFactory.getLog(this.getClass());

    public LazyCollectionNullifierFilter() {
        if (log.isDebugEnabled()) log.debug(AopLogManager.START + AopLogManager.CONSTRUCTOR_LABEL + this.getClass().getSimpleName() + "()");
        if (log.isDebugEnabled()) log.debug(AopLogManager.END + AopLogManager.CONSTRUCTOR_LABEL + this.getClass().getSimpleName() + "()");
    }

    @PostConstruct
    public void init() {
        if (log.isDebugEnabled()) log.debug(AopLogManager.START + this.getClass().getSimpleName() + ".init()");
        if (log.isDebugEnabled()) log.debug(AopLogManager.END + this.getClass().getSimpleName() + ".init()");
    }

    @PreDestroy
    public void destroy() {
        if (log.isDebugEnabled()) log.debug(AopLogManager.START + this.getClass().getSimpleName() + ".destroy()");
        if (log.isDebugEnabled()) log.debug(AopLogManager.END + this.getClass().getSimpleName() + ".destroy()");
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        if (log.isDebugEnabled()) log.debug(AopLogManager.START + this.getClass().getSimpleName() + ".filter(..)");
        final StopWatch sw = new StopWatch();
        sw.start();

        try {
            if (containerResponseContext.getEntity() instanceof ApiResult) {
                final LazyCollectionNullifier nullifier = new LazyCollectionNullifier();
                nullifier.execute(((ApiResult) containerResponseContext.getEntity()).getData());
            }
        } finally {
            sw.stop();
            if (log.isDebugEnabled()) log.debug(AopLogManager.END + this.getClass().getSimpleName() + ".filter(..). " + AopLogManager.ELAPSED_LABEL + (sw.getTime()) + AopLogManager.MILLISECONDS_LABEL);
        }
    }
}
