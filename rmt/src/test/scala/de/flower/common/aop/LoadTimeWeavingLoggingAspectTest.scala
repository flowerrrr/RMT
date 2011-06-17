package de.flower.common.aop

import de.flower.test.AbstractIntegrationTests
import org.testng.annotations.Test
import de.flower.common.aop.LoggingAspectTest.FooBar


/**
 *
 * @author oblume
 */

class LoadTimeWeavingLoggingAspectTest extends AbstractIntegrationTests {

    @Test
    def testAspect() {
        LoggingAspectTest.runTest()
    }

}