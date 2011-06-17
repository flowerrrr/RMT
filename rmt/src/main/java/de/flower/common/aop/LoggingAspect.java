package de.flower.common.aop;

import org.aspectj.lang.annotation.Pointcut;

/**
 * @author oblume
 */
// @Aspect
public class LoggingAspect extends AbstractSlf4JLoggingAspect {

    @Pointcut("execution(* de.flower.common.validation..*(..))")
    protected void entry() {
    }



}
