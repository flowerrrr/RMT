package de.flower.common.aop;

import de.flower.common.logging.Slf4jUtil;
import org.slf4j.Logger;
import org.testng.annotations.Test;

/**
 * @author oblume
 */
public class LoggingAspectTest {

    private final static Logger log = Slf4jUtil.getLogger();


    @Test
    public void testAspect() {
        FooBar foobar = new FooBar  ();
        String s = foobar.method("hello", " world")     ;
        log.info(s);
    }

    public static class FooBar {

        public String method(String s1, String s2) {
            return s1 + s2;
        }
    }

}
