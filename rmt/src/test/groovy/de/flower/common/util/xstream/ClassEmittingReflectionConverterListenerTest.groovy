package de.flower.common.util.xstream

import org.testng.annotations.Test

/**
 * @author flowerrrr
 */
public class ClassEmittingReflectionConverterListenerTest {

    @Test
    public void testCounting() {
        def listener = new ObjectSerializationListener();
        listener.reset();
        listener.notify("hello world");
        listener.notify(new Integer(1));
        listener.notify(new Integer(2));
        listener.flush()
    }

    @Test
    public void testWhiteListing() {
        def listener = new ObjectSerializationListener();
        listener.setWhiteList(["java.lang.String", "java.lang.Object", ".*List"])
        listener.reset();
        listener.notify("hello world");
        listener.notify(new Object() {
            // to test filtering of inner classes
        });
        listener.notify(new ArrayList());
        listener.flush()
    }
}
