package de.flower.common.aop

import de.flower.rmt.test.AbstractRMTIntegrationTests
import org.testng.annotations.Test


class LoadTimeWeavingLoggingAspectTest extends AbstractRMTIntegrationTests {

    @Test(expectedExceptions = [AssertionError])
    def void testAspect() {
        LoggingAspectTest.runTest()
    }

}