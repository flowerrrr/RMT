package de.flower.test

import org.slf4j.{LoggerFactory, Logger}
import org.testng.{Assert, TestListenerAdapter, ITestResult}

/**
 *
 * @author flowerrrr
 */

class ExceptionLoggerTestListener extends TestListenerAdapter {

    private final val log: Logger = LoggerFactory.getLogger(getClass)

    override def onTestFailure(tr: ITestResult): Unit = {
        log.error(tr.toString, tr.getThrowable)
    }

    override def onTestSkipped(tr: ITestResult): Unit = {
        log.error(tr.toString, tr.getThrowable)
    }

}