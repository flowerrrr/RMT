package de.flower.common.util

import org.testng.annotations.Test

import static org.testng.Assert.assertTrue
/**
 * Test class in grooy.
 * Groovy generated anonymous classes seemed to behave differently from java classes.
 */
@groovy.transform.TypeChecked
public class GroovyClazzTest {

    /**
     * Class#isAnonymousClass() was broken in groovy-compiled classes.
     */
    @Test
    def void testIsAnonymousClassIsFixed() {
        def Object o = new Object() {

        }
        assertTrue(Clazz.isAnonymousClass(o.getClass()))
    }

}
