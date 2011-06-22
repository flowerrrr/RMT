package de.flower.test

import org.testng.annotations.BeforeMethod._
import java.lang.reflect.Method
import org.testng.annotations.{AfterMethod, BeforeMethod}
import org.testng.annotations.AfterMethod._
import org.slf4j.{LoggerFactory, Logger}

/**
 * 
 * @author oblume
 */

trait TestMethodListener {

    private val log: Logger = LoggerFactory.getLogger(this.getClass());


    /**
     * Test started.
     *
     * @param method the method
     */
    @BeforeMethod(alwaysRun = true)
    def testStarted(method: Method): Unit = {
        log.info("***************************************************************")
        log.info("Test [" + method.getName + "] started.")
        log.info("***************************************************************")
    }

    /**
     * Test finished.
     *
     * @param method the method
     */
    @AfterMethod(alwaysRun = true)
    def testFinished(method: Method): Unit = {
        log.info("***************************************************************")
        log.info("Test [" + method.getName + "()] finshed.")
        log.info("***************************************************************")
    }


}