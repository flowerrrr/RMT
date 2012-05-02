package de.flower.common.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.boolex.EvaluationException;
import ch.qos.logback.core.boolex.EventEvaluatorBase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Class to throttle sending of mails through logbacks SMTPAppender.
 *
 * @author flowerrrr
 */
public class SMTPEvaluator extends EventEvaluatorBase<ILoggingEvent> {

    private Date lastTriggerDate;

    /**
     * Min time between two mails. 1 h.
     */
    private static final long idleTimeSecs = TimeUnit.HOURS.toMillis(1);

    /**
     * List of regex expressions to match against event. if match ignore event.
     */
    private List<String> excludes = new ArrayList<>();

    public SMTPEvaluator() {
        // let app sent email on this message. helps to verify that email sending is working.
        // excludes.add(".*\\QIllegal argument on static metamodel field injection : de.flower.rmt.model.event.AbstractSoccerEvent_#surfaceList\\E.*");
    }

    @Override
    public boolean evaluate(final ILoggingEvent event) throws NullPointerException, EvaluationException {
        // event to be ignored?
        if (ignoreEvent(event)) {
            return false;
        } else if (insideIdlePeriod()) {
            return false;
        } else {
            lastTriggerDate = new Date();
            return true;
        }

    }

    private boolean insideIdlePeriod() {
        if (lastTriggerDate == null) {
            return false;
        } else {
            long diff = new Date().getTime() - lastTriggerDate.getTime();
            return (diff < idleTimeSecs);
        }
    }

    private boolean ignoreEvent(final ILoggingEvent event) {
        String msg = event.getFormattedMessage();
        for (String exclude : excludes) {
            if (msg.matches(exclude)) {
                return true;
            }
        }
        return false;
    }
}
