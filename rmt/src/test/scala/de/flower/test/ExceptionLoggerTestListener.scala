package de.flower.test

import org.testng.{TestListenerAdapter, ITestResult}
import org.slf4j.{LoggerFactory, Logger}

/**
 *
 * @author flowerrrr
 */

class ExceptionLoggerTestListener extends TestListenerAdapter {

    private final val log: Logger = LoggerFactory.getLogger(getClass)

    override def onTestFailure(tr: ITestResult): Unit = {
        log.error(tr.toString, tr.getThrowable)
    }
}