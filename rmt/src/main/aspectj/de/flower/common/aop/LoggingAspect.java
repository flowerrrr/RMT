package de.flower.common.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author oblume
 */
@Aspect
public class LoggingAspect extends AbstractSlf4JLoggingAspect {

    @Pointcut("execution(* de.flower..*(..)) && !execution(* get*(..))")
    protected void entry() {
    }

    @Pointcut("execution(de.flower..new(..))")
    protected void ctor() {

    }

}
