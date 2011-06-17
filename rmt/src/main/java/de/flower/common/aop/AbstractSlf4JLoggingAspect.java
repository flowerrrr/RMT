package de.flower.common.aop;

import de.flower.common.logging.Slf4jUtil;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;

/**
 * @author oblume
 */
public abstract class AbstractSlf4JLoggingAspect extends AbstractLoggingAspect {

    private final static Logger log = Slf4jUtil.getLogger();

    @Override
    protected void logEnter(JoinPoint.StaticPart jp) {
        if (log.isTraceEnabled()) {
            log.info("enter " + jp.getSignature());
        }
    }

    @Override
    protected void logExit(JoinPoint.StaticPart jp) {
        if (log.isTraceEnabled()) {
            log.trace("exit");
        }
    }

}

