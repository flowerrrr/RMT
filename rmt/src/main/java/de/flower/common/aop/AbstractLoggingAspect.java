package de.flower.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author oblume
 */
public abstract class AbstractLoggingAspect {

    @Pointcut("")
    abstract protected void entry();

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

    @Before("trace()")
    public void _logEnter(JoinPoint jp) {
        logEnter(jp, true);
    }

    @After("trace()")
    public void _logExit(JoinPoint.StaticPart jp) {
        logExit(jp);
    }

    abstract protected void logEnter(JoinPoint jp, boolean indent);

    abstract protected void logExit(JoinPoint.StaticPart jp);

}
