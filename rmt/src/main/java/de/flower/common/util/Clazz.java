package de.flower.common.util;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * http://stackoverflow.com/questions/936684/getting-the-class-name-from-a-static-method-in-java
 *
 * @author flowerrrr
 */
public final class Clazz {

    private Clazz() {

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
     * @param callee
     * @return
     */
    public static Class<?> getCallingClassStatic(final Class<?> callee) {
        CurrentClassGetter ccg = new CurrentClassGetter();
        final Class[] classContext = ccg.getClassContext2();
        int index = -1;
        for (int i = 0; i < classContext.length; i++) {
            if (classContext[i].equals(callee)) {
                index = i;
                break;
            }
        }
        // search for next class in stack that differs from clazz
        for (int i = index; i < classContext.length; i++) {
            Class<?> callingClass = classContext[i];
            if (!callingClass.equals(callee)) {
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

    public static String getShortName(final Class<?> clazz) {
        if (isAnonymousInnerClass(clazz)) {
            return clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1);
        } else {
            return clazz.getSimpleName();
        }
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

    public static boolean isAnonymousInnerClass(Class<?> clazz) {
        // return clazz.getName().contains("$") && clazz.getSimpleName().equals("");
        return clazz.getEnclosingClass() != null && clazz.getName().contains("$") && clazz.getSimpleName().equals("");
    }

    public static Class<?> getSuperClass(Class<?> anonymousClass) {
        Check.isTrue(isAnonymousInnerClass(anonymousClass));
        return anonymousClass.getSuperclass();
    }

    /**
     * Returns class hierarchy. Starts at object.getClass() and descends down to baseClass.
     *
     * @param object
     * @param baseClass
     * @return
     */
    public static List<Class<?>> getClassList(Class<?> subClass, Class<?> baseClass) {
        List<Class<?>> classes = Lists.newArrayList();
        Class<?> c = subClass;
        classes.add(c);
        while (!c.equals(baseClass)) {
            c = c.getSuperclass();
            if (c == null) {
                throw new IllegalArgumentException(String.format("Baseclass [%s] not part of type hierarchy of [%s]", baseClass, subClass));
            }
            classes.add(c);
        }
        return classes;
    }
}
