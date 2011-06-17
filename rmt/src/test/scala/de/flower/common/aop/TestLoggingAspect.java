package de.flower.common.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author oblume
 */
@Aspect
public class TestLoggingAspect extends AbstractSlf4JLoggingAspect {

    @Pointcut("execution(* de.flower.common.aop.LoggingAspectTest..*(..))")
    protected void trace() {
    }

}
