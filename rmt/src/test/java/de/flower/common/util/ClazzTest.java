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
        assertFalse(Clazz.isAnonymousInnerClass(InnerStaticClass.class));
        assertFalse(Clazz.isAnonymousInnerClass(InnerClass.class));
    }

    @Test
    public void testGetSuperClass() {
        Object o = new Object() {
        };
        o.getClass().getSuperclass();
        assertTrue(Clazz.isAnonymousInnerClass(o.getClass()));
        assertEquals(Clazz.getSuperClass(o.getClass()), Object.class);
    }

    @Test
    public void testGetShortName() {
        assertEquals(Clazz.getShortName(String.class), "String");
        assertEquals(Clazz.getShortName(new Object() {}.getClass()), "ClazzTest$3");
        assertEquals(Clazz.getShortName(InnerStaticClass.class), "InnerStaticClass");
        assertEquals(Clazz.getShortName(InnerClass.class), "InnerClass");
    }

    public static class InnerStaticClass {

    }

    public class InnerClass {

    }
}
