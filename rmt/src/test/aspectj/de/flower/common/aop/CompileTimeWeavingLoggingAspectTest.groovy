package de.flower.common.aop

import org.testng.annotations.Test

/**
 * Tests the compile time weaving of aspects.
 * In order to have reliable test results of the load time weaving test we must
 * make sure, that there are no advices compiled into the code.
 * @author flowerrrr
 */
@Deprecated // does not work
class CompileTimeWeavingLoggingAspectTest extends LoggingAspectTest {

    @Test(enabled = false)
    def void testAspect() {
        super.testAspect()
    }
}