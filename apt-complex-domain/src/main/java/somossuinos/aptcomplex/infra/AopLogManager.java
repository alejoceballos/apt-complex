package somossuinos.aptcomplex.infra;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * @author ceballos
 * @since 12/10/15.
 */
@Aspect
@Named
@Singleton
public class AopLogManager {

    private Log log = LogFactory.getLog(this.getClass());

    public static final String START = ">> ";
    public static final String END = "<< ";
    public static final String CONSTRUCTOR_LABEL = "(CONSTRUCTOR) ";
    public static final String ELAPSED_LABEL = "Elapsed time: ";
    public static final String MILLISECONDS_LABEL = " ms";

    public AopLogManager() {
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

    @Pointcut("execution(* somossuinos.aptcomplex.service..*.*(..))")
    private void aroundServices() { }

    @Around("aroundServices()")
    public Object around(final ProceedingJoinPoint pjp) throws Throwable {
        final String className = pjp.getTarget().getClass().getSimpleName();
        final String methodName = pjp.getSignature().getName();

        final Log targetLog = LogFactory.getLog(pjp.getTarget().getClass());

        final String logId = className + "." + methodName + "(..)";
        if (targetLog.isDebugEnabled()) targetLog.debug(START + logId);

        final StopWatch sw = new StopWatch();
        sw.start();

        try {
            return pjp.proceed(pjp.getArgs());

        } finally {
            sw.stop();
            if (targetLog.isDebugEnabled()) targetLog.debug(END + logId + ". " + ELAPSED_LABEL + (sw.getTime()) + MILLISECONDS_LABEL);
        }
    }

}
