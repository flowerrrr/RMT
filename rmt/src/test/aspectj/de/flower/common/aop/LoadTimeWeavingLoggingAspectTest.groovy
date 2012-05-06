package de.flower.common.aop

import de.flower.rmt.test.AbstractIntegrationTests
import org.testng.annotations.Test

/**
 *
 * @author flowerrrr
 */

class LoadTimeWeavingLoggingAspectTest extends AbstractIntegrationTests {

    @Test(expectedExceptions = [AssertionError])
    def void testAspect() {
        LoggingAspectTest.runTest()
    }

}