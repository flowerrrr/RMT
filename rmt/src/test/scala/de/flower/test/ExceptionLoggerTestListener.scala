package de.flower.test

import org.slf4j.Logger
import de.flower.common.util.logging.Slf4jUtil
import org.testng.{TestListenerAdapter, ITestResult}

/**
 *
 * @author oblume
 */

class ExceptionLoggerTestListener extends TestListenerAdapter {

    private final val log: Logger = Slf4jUtil.getLogger

    override def onTestFailure(tr: ITestResult): Unit = {
        log.error(tr.toString, tr.getThrowable)
    }
}