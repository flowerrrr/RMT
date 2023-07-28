package de.flower.rmt.test;

import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.fail;


public class Assert {

    public static void assertContains(String s, String sub) {
        assertNotNull(s);
        assertNotNull(sub);
        boolean condition = s.contains(sub);
        if (!condition) {
            fail("[" + s + "] not contains [" + sub + "]");
        }
    }

}
