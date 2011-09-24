package de.flower.common.aop

import de.flower.test.AbstractIntegrationTests
import org.testng.annotations.Test


/**
 *
 * @author flowerrrr
 */

class LoadTimeWeavingLoggingAspectTest extends AbstractIntegrationTests {

    @Test(expectedExceptions = Array(classOf[AssertionError]))
    def testAspect() {
        LoggingAspectTest.runTest
    }

}