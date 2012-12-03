package de.flower.common.util;

/**
 * Utility class to output log messages in a format that can't be overlooked.
 *
 * @author flowerrrr
 */
public class S {

    @Deprecated // should only be used for JRebel debug sessions
    public static void log(String s) {
        System.out.println("******************************************************");
        System.out.println(s);
        System.out.println("******************************************************");
    }
}
