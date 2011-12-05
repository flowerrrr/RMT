package de.flower.common.aop

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.filter.Filter
import ch.qos.logback.core.spi.FilterReply
import de.flower.common.test.mock.LogBackListAppender
import org.slf4j.LoggerFactory
import org.testng.annotations.BeforeMethod
import org.testng.Assert._
import de.flower.rmt.model.{Club, Team}
import de.flower.common.test.mock.IListAppender

/**
 * Test written in java cause aspectj will not work with scala classes when using
 * compile time weaving.
 * <p/>
 * Requires that the aspect {@link TestLoggingAspect} is compiled and aspectj compiler is running as part
 * of the build.
 *
 * @author flowerrrr
 */
object LoggingAspectTest {

    def runTest: Unit = {
        val test = new LoggingAspectTest
        test.initTest
        test.testAspect
    }
}

class LoggingAspectTest {

    private var listAppender: IListAppender[ILoggingEvent] = _

    private val loggerPrefix  = "trace"

    private val traceLogger = LoggerFactory.getLogger(loggerPrefix).asInstanceOf[ch.qos.logback.classic.Logger]

    @BeforeMethod
    def initTest: Unit = {
        listAppender = LogBackListAppender.configureListAppender
        listAppender.addFilter(new Filter[ILoggingEvent] {
            def decide(event: ILoggingEvent): FilterReply = {
                return if ((event.getLoggerName.startsWith(loggerPrefix))) FilterReply.ACCEPT else FilterReply.DENY
            }
        })
        traceLogger.setLevel(Level.TRACE)
    }

    def testAspect: Unit = {
        var team: Team = new Team(new Club("foo club"))
        team.setName("foobar")
        team.getUrl
        team == new Team(new Club("foo club 2"))
        assertTrue(listAppender.getList.isEmpty == false, "No tracing log output found.")
    }

}