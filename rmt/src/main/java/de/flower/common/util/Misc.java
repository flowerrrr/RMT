package de.flower.common.util;

/**
 * http://stackoverflow.com/questions/936684/getting-the-class-name-from-a-static-method-in-java
 *
 * @author flowerrrr
 */
public final class Misc {

    private Misc() {

    }

    /**
     * Get the class of the caller in a static method.
     *
     * @return class
     */
    public static Class<?> getClassStatic() {
        return getClassStatic(3);
    }

    /**
     * Returns the class that is calling the caller of this method.
     * <p/>
     * If A has a method that calls this method and B calls A's method, then
     * B is returned.
     *
     * @return
     */
    public static Class<?> getCallingClassStatic() {
        CurrentClassGetter ccg = new CurrentClassGetter();
        final Class[] classContext = ccg.getClassContext2();
        Class<?> clazz = classContext[3];
        // search for next class in stack that differs from clazz
        for (int i = 4; i < classContext.length; i++) {
            Class<?> callingClass = classContext[i];
            if (!callingClass.equals(clazz)) {
                return callingClass;
            }
        }
        throw new RuntimeException("This should not happen");
    }

    public static Class<?> getClassStatic(int frame) {
        // noinspection RawUseOfParameterizedType
        CurrentClassGetter ccg = new CurrentClassGetter();

        return ccg.getClass(frame);
    }

    private static class CurrentClassGetter extends SecurityManager {

        public Class getClass(int frame) {
            final Class[] classContext = getClassContext();
            return classContext[frame];
        }

        public Class[] getClassContext2() {
            return getClassContext();
        }
    }
}
