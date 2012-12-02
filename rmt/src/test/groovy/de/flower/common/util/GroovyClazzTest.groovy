package de.flower.common.util
import org.testng.annotations.Test

import static org.testng.Assert.assertFalse
/**
 * Test class in grooy.
 * Groovy generated anonymous classes seem to behave differently from java classes.
 *
 * @author flowerrrr
 */
@groovy.transform.TypeChecked
public class GroovyClazzTest {

    /**
     * Class#isAnonymousClass() is broken in groovy-compiled classes.
     */
    @Test
    def void testIsAnonymousClassIsBroken() {
        def Object o = new Object() {

        }
        assertFalse(Clazz.isAnonymousClass(o.getClass()))
    }

}
