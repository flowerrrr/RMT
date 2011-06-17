package de.flower.common.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author oblume
 */
@Aspect
@Deprecated
public class TestLoggingAspect extends AbstractSlf4JLoggingAspect {

    @Pointcut("execution(* de.flower..*(..))")
    protected void entry() {
    }

}
