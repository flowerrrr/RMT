package de.flower.test.logging

import org.slf4j.LoggerFactory
import java.io.{FileDescriptor, FileOutputStream}
import ch.qos.logback.classic.{Level, Logger, PatternLayout, LoggerContext}
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.OutputStreamAppender

/**
 *
 * @author oblume
 */

class GradleLogAppender {
    def initGradleAppender(): Unit = {
        var lc: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
        var appender: OutputStreamAppender[ILoggingEvent] = new OutputStreamAppender[ILoggingEvent]
        appender.setContext(lc)
        var outputStream: FileOutputStream = new FileOutputStream(FileDescriptor.out)
        var layout: PatternLayout = new PatternLayout
        layout.setPattern("[%level] [%c] %m%n%ex")
        layout.setContext(lc)
        layout.start
        appender.setLayout(layout)
        appender.setOutputStream(outputStream)
        var root: Logger = lc.getLogger("ROOT")
        root.addAppender(appender)
        root.setLevel(Level.ALL)
        appender.start
    }
}

object GradleLogAppender {

}
