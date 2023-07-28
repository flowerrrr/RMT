package de.flower.common.test.mock;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.AppenderBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class LogBackListAppender extends AppenderBase<ILoggingEvent> implements Appender<ILoggingEvent> {

    private List<ILoggingEvent> list = new ArrayList<ILoggingEvent>();

    public LogBackListAppender() {
    }

    public List<ILoggingEvent> getList() {
        return list;
    }

    /**
     * Clears the internal event list.
     * Should be called before a new test method is invoked.
     */
    public void reset() {
        list.clear();
    }

    private boolean hasException(Class<Throwable> claz) {
        for (ILoggingEvent event : getList()) {
            IThrowableProxy throwableInformation = event.getThrowableProxy();
            if (throwableInformation != null) {
                String throwable = throwableInformation.getClassName();
                if (throwable == claz.getCanonicalName()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasMessage(String message) {
        for (ILoggingEvent event : getList()) {
            String logMessage = event.getFormattedMessage();
            if (logMessage != null && (logMessage == message)) {
                return true;
            }
        }
        return false;
    }

    private String format(ILoggingEvent loggingEvent) {
        throw new UnsupportedOperationException("Feature not implemented!");
    }

    protected void append(ILoggingEvent e) {
        list.add(e);
    }

    public static LogBackListAppender configureListAppender() {
        LogBackListAppender appender = new LogBackListAppender();
        appender.start();
        addAppender2Root(appender);
        return appender;
    }

    public static void addAppender2Root(Appender<ILoggingEvent> appender) {
        ch.qos.logback.classic.Logger rootLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.addAppender(appender);
    }
}

