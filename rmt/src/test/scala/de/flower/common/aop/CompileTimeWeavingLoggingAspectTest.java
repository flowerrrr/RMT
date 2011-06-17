package de.flower.common.aop;

import org.testng.annotations.Test;

/**
 * Tests the compile time weaving of aspects.
 * In order to have reliable test results of the load time weaving test we must
 * make sure, that there are no advices compiled into the code.
 * @author oblume
 */
public class CompileTimeWeavingLoggingAspectTest extends LoggingAspectTest {

    @Test(expectedExceptions = { AssertionError.class})
    public void testAspect() {
        super.testAspect();
    }
}
