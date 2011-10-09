package de.flower.common.aop

import org.testng.annotations.Test
import de.flower.rmt.test.AbstractIntegrationTests

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