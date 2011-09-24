package de.flower.test.mock

import ch.qos.logback.classic.PatternLayout
import ch.qos.logback.classic.spi.{IThrowableProxy, ILoggingEvent}
import org.slf4j.{Logger, LoggerFactory}
import collection.mutable.ListBuffer
import ch.qos.logback.core.{AppenderBase, Appender}

/**
 *
 * @author flowerrrr
 */
class LogBackListAppender extends AppenderBase[ILoggingEvent] with IListAppender[ILoggingEvent] {

    private val layout: PatternLayout = new PatternLayout

    layout.setPattern("[%5p] - %t - %c{3} - %m%n")

    private val list = new ListBuffer[ILoggingEvent]()


    def getList: List[ILoggingEvent] = {
        return list.toList
    }

    /**
     * Clears the internal event list.
     * Should be called before a new test method is invoked.
     */
    def reset() {
        list.clear
    }

    def hasException(claz: Class[Throwable]): Boolean = {
        for (event <- getList) {
            var throwableInformation: IThrowableProxy = event.getThrowableProxy
            if (throwableInformation != null) {
                var throwable: String = throwableInformation.getClassName
                if (throwable == claz.getCanonicalName) {
                    return true
                }
            }
        }
        return false
    }

    def hasMessage(message: String): Boolean = {
        for (event <- getList) {
            var logMessage: String = event.getFormattedMessage
            if (logMessage != null && (logMessage == message)) {
                return true
            }
        }
        return false
    }

    def format(loggingEvent: ILoggingEvent): String = {
        throw new UnsupportedOperationException("Feature not implemented!")
    }

    protected def append(e: ILoggingEvent) {
        list += e
    }
}

object LogBackListAppender {

    def configureListAppender(): IListAppender[ILoggingEvent] = {
        var appender = new LogBackListAppender()
        appender.start()
        addAppender2Root(appender)
        return appender
    }

    def addAppender2Root(appender: Appender[ILoggingEvent]) {
        var rootLogger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME).asInstanceOf[ch.qos.logback.classic.Logger]
        rootLogger.addAppender(appender)
    }

}

