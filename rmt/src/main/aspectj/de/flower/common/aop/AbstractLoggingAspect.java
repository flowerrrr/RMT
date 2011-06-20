package de.flower.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author oblume
 */
@Aspect
public abstract class AbstractLoggingAspect {

    @Pointcut("")
    abstract protected void entry();

    @Pointcut("")
    protected abstract void ctor();

    @Pointcut("entry() && !within(AbstractLoggingAspect+)")
    protected void trace() {

    }

    @Pointcut("ctor() && !within(AbstractLoggingAspect+)")
    protected void _ctor() {

    }

    @Before("_ctor()")
    public void _ctorEnter(JoinPoint jp) {
        logEnter(jp, false);
    }


/*
    Does not work. Produces this error in unit tests: Caused by: java.lang.VerifyError: (class: de/flower/rmt/model/Team, method: setName signature: (Ljava/lang/String;)V) Incompatible object argument for function call
    Will implement stop watch with logEnter and logExit.
    @Around("trace()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        long timeStarted = System.currentTimeMillis();
        logEnter(pjp, true);
        try {
            return pjp.proceed();
        } finally {
            long time = System.currentTimeMillis() - timeStarted;
            logExit(pjp.getStaticPart(), time);
        }
    }
*/

    @Before("trace()")
    public void _logEnter(JoinPoint jp) {
        logEnter(jp, true);
    }

    @After("trace()")
    public void _logExit(JoinPoint.StaticPart jp) {
        logExit(jp);   // don't log execution time.
    }

    abstract protected void logEnter(JoinPoint jp, boolean indent);

    abstract protected void logExit(JoinPoint.StaticPart jp);

}
