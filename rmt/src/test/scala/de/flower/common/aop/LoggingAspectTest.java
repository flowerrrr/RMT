package de.flower.common.aop;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import de.flower.common.logging.Slf4jUtil;
import de.flower.rmt.model.Team;
import de.flower.test.mock.IListAppender;
import de.flower.test.mock.LogBackListAppender;
import net.jcip.annotations.NotThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;

import static org.testng.Assert.*;

/**
 * Test written in java cause aspectj will not work with scala classes when using
 * compile time weaving.
 * <p/>
 * Requires that the aspect {@link TestLoggingAspect} is compiled and aspectj compiler is running as part
 * of the build.
 *
 * @author oblume
 */
public class LoggingAspectTest {

    private final static Logger log = Slf4jUtil.getLogger();

    private IListAppender<ILoggingEvent> listAppender;

    private final String loggerPrefix = "trace";

    private ch.qos.logback.classic.Logger traceLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(loggerPrefix);

    @BeforeMethod
    public void initTest() {
        listAppender = LogBackListAppender.configureListAppender();
        listAppender.addFilter(new Filter<ILoggingEvent>() {
            @Override
            public FilterReply decide(ILoggingEvent event) {
                return (event.getLoggerName().startsWith(loggerPrefix)) ? FilterReply.ACCEPT : FilterReply.DENY;
            }
        });
        // set loglevel of trace logger to trace. otherwise we would not see any output
        traceLogger.setLevel(Level.TRACE);
    }

    public void testAspect() {
        // call into de.flower.rmt package to trigger tracing. must call a class that is
        // a) instrumented and
        // b) matches a pointcut
        Team team = new Team();
        team.setName("foobar");
        team.equals(new Team());

        // read out the mockappender and look for output from the logging aspect
        assertTrue(listAppender.getList().isEmpty() == false, "No tracing log output found.");
    }

    public static void runTest() {
        LoggingAspectTest test = new LoggingAspectTest();
        test.initTest();
        test.testAspect();
    }

    public static class FooBar {

        public String method(String s1, String s2) {
            return s1 + s2;
        }
    }

}
