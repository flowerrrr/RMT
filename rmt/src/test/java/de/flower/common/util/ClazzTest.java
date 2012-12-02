package de.flower.common.util;

import com.google.common.collect.Lists;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */
public class ClazzTest {

    @Test
    public void testGetCallingClassStatic() {
        class A {

            public Class<?> callingClass;

            public A() {
                callingClass = Clazz.getCallingClassStatic(A.class);
            }
        }
        ;
        class B extends A {

            public B() {
                callingClass = Clazz.getCallingClassStatic(B.class);
            }
        }
        ;
        class C extends B {

            public C() {
                callingClass = Clazz.getCallingClassStatic(C.class);
            }
        }

        assertEquals(new A().callingClass, ClazzTest.class);
        assertEquals(new B().callingClass, ClazzTest.class);
        assertEquals(new C().callingClass, ClazzTest.class);
    }

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
        assertEquals(Clazz.getShortName(new Object() {
        }.getClass()), "ClazzTest$3");
        assertEquals(Clazz.getShortName(InnerStaticClass.class), "InnerStaticClass");
        assertEquals(Clazz.getShortName(InnerClass.class), "InnerClass");
    }

    @Test
    public void testGetClassList() {
        class A {

        };
        class B extends A {

        }   ;
        class C  {

        }
        List<Class<?>> classes = Clazz.getClassList(B.class, A.class);
        assertEquals(classes, Lists.newArrayList(B.class, A.class));
        classes = Clazz.getClassList(C.class, Object.class);
        assertEquals(classes, Lists.newArrayList(C.class, Object.class));
        try {
            Clazz.getClassList(C.class, A.class);
            fail();
        } catch (IllegalArgumentException e) {
            // what we expect
        }
    }

    public static class InnerStaticClass {

    }

    public class InnerClass {

    }
}
