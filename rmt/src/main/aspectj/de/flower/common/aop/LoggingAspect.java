package de.flower.common.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


@Aspect
public class LoggingAspect extends AbstractSlf4JLoggingAspect {

    @Pointcut("execution(* de.flower..*.*(..))") //  && !execution(* get*(..))")
    @Override
    protected void entry() {
    }

    @Pointcut("execution(de.flower..new(..))")
    @Override
    protected void ctor() {

    }

}
