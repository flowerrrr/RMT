package de.flower.common.util;

/**
 * @author flowerrrr
 */
public class Misc {

    /**
     * Get the class of the caller in a static method.
     *
     * @return class
     */
    @SuppressWarnings( {"RawUseOfParameterizedType"})
    public static Class getClassStatic() {
        return getClassStatic(3);
    }

    public static Class<?> getClassStatic(int frame) {
        // noinspection RawUseOfParameterizedType
        class CurrentClassGetter extends SecurityManager {

            public Class getClass(int frame) {
                return getClassContext()[frame];
            }
        }
        CurrentClassGetter ccg = new CurrentClassGetter();

        return ccg.getClass(frame);
    }

}
