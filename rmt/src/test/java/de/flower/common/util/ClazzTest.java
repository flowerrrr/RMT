package de.flower.common.util;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */
public class ClazzTest {

    @Test
    public void testIsAnonymousClass() {
        Object o = new Object();
        assertFalse(Clazz.isAnonymousInnerClass(o.getClass()));
        o = new Object() {
            @Override
            public String toString() {
                return "foo";
            }
        };
        assertTrue(Clazz.isAnonymousInnerClass(o.getClass()));
    }

    @Test
    public void testGetSuperClassName() {
        Object o = new Object() {
        };
        o.getClass().getSuperclass();
        assertTrue(Clazz.isAnonymousInnerClass(o.getClass()));
        assertEquals(Clazz.getSuperClassName(o.getClass()), Object.class.getSimpleName());
    }
}
