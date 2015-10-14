package somossuinos.aptcomplex.infra;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

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

    public static final String START = ">> ";
    public static final String END = "<< ";
    public static final String CONSTRUCTOR_LABEL = "(CONSTRUCTOR) ";
    public static final String ELAPSED_LABEL = "Elapsed time: ";
    public static final String MILLISECONDS_LABEL = " ms";

    @Pointcut("execution(* somossuinos.aptcomplex.service..*.*(..))")
    private void aroundServices() { }

    @Around("aroundServices()")
    public Object around(final ProceedingJoinPoint pjp) throws Throwable {
        final String className = pjp.getTarget().getClass().getSimpleName();
        final String methodName = pjp.getSignature().getName();

        final Log log = LogFactory.getLog(pjp.getTarget().getClass());

        final String logId = className + "." + methodName + "(..)";
        if (log.isDebugEnabled()) log.debug(START + logId);

        final StopWatch sw = new StopWatch();
        sw.start();

        try {
            return pjp.proceed(pjp.getArgs());

        } finally {
            sw.stop();
            if (log.isDebugEnabled()) log.debug(END + logId + ". " + ELAPSED_LABEL + (sw.getTime()) + MILLISECONDS_LABEL);
        }
    }

}
