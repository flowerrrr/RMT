package de.flower.common.util;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */
public class MiscTest {

    @Test
    public void testIsAnonymousClass() {
        Object o = new Object();
        assertFalse(Misc.isAnonymousInnerClass(o.getClass()));
        o = new Object() {
            @Override
            public String toString() {
                return "foo";
            }
        };
        assertTrue(Misc.isAnonymousInnerClass(o.getClass()));
    }

    @Test
    public void testGetSuperClassName() {
        Object o = new Object() {
        };
        o.getClass().getSuperclass();
        assertTrue(Misc.isAnonymousInnerClass(o.getClass()));
        assertEquals(Misc.getSuperClassName(o.getClass()), Object.class.getSimpleName());
    }
}
