package de.flower.common.util.xstream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.ArrayList;


public class ObjectSerializationListenerTest {

    private final static Logger log = LoggerFactory.getLogger(ObjectSerializationListenerTest.class);

    @Test
    public void testCounting() {
        ObjectSerializationListener listener = new ObjectSerializationListener();
        ObjectSerializationListener.reset();
        listener.notify("hello world");
        listener.notify(new Integer(1));
        listener.notify(new Integer(2));
        listener.notify(new Object() {
            // to test filtering of inner classes
        });
        listener.notify(new ArrayList());
        ObjectSerializationListener.Context context = ObjectSerializationListener.getContext();
        log.info(context.toString());
    }
}
