package de.flower.common.aop

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.filter.Filter
import ch.qos.logback.core.spi.FilterReply
import de.flower.common.test.mock.IListAppender
import de.flower.common.test.mock.LogBackListAppender
import de.flower.rmt.model.Club
import de.flower.rmt.model.Team
import org.slf4j.LoggerFactory
import org.testng.annotations.BeforeMethod
import static org.testng.Assert.assertTrue

/**
 *
 * @author flowerrrr
 */
@Deprecated // not sure what this test is all about
class LoggingAspectTest {

    IListAppender<ILoggingEvent> listAppender

    def loggerPrefix = "trace"

    def traceLogger = LoggerFactory.getLogger(loggerPrefix)  as Logger

    @BeforeMethod
    def void initTest() {
        listAppender = LogBackListAppender.configureListAppender()
        listAppender.addFilter(new Filter<ILoggingEvent>() {
            def FilterReply decide(ILoggingEvent event) {
                return (event.getLoggerName().startsWith(loggerPrefix)) ? FilterReply.ACCEPT : FilterReply.DENY
            }
        })
        traceLogger.setLevel(Level.TRACE)
    }

    def void testAspect() {
        def team = new Team(new Club("foo club"))
        team.setName("foobar")
        team.getUrl()
        team == new Team(new Club("foo club 2"))
        assertTrue(listAppender.getList().isEmpty() == false, "No tracing log output found.")
    }

    static runTest() {
         def test = new LoggingAspectTest()
         test.initTest()
         test.testAspect()
     }


}