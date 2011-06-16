package de.flower.rmt;

import de.flower.common.logging.Slf4jUtil;
import org.slf4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Paste any java code you want to transform to scala first in this class. let class compile successfully.
 * then copy java code and paste it into a scala class.
 *
 * @author oblume
 */
public class Java2ScalaCopyContainer {

    private final static Logger log = Slf4jUtil.getLogger();


    /**
     * Test started.
     *
     * @param method the method
     */
    @BeforeMethod(alwaysRun = true)
    public void testStarted(java.lang.reflect.Method method) {
        log.info("***************************************************************");
        log.info("Test [" + method.getName() + "] started.");
        log.info("***************************************************************");
    }

    /**
     * Test finished.
     *
     * @param method the method
     */
    @AfterMethod(alwaysRun = true)
    public void testFinished(java.lang.reflect.Method method) {
        log.info("***************************************************************");
        log.info("Test [" + method.getName() + "()] finshed.");
        log.info("***************************************************************");
    }

}
