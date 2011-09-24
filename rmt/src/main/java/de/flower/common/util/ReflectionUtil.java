package de.flower.common.util;

import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author flowerrrr
 */
public class ReflectionUtil {


    public static Object getProperty(Object bean, String name) {
        try {
            return PropertyUtils.getProperty(bean, name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setProperty(Object bean, String name, Object value) {
        try {
            PropertyUtils.setProperty(bean, name, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setField(Object bean, String fieldName, Object value) {

        try {
            Class<?> claz = bean.getClass();
            Field field = claz.getDeclaredField(fieldName);
            // set accessible true
            field.setAccessible(true);
            field.set(bean, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static Object getField(Object bean, String fieldName) {

         try {
             Class<?> claz = bean.getClass();
             Field field = findField(claz, fieldName);
             // set accessible true
             field.setAccessible(true);
             return field.get(bean);
         } catch (Exception e) {
             throw new RuntimeException(e);
         }

     }

    private static Field findField(Class<?> claz, String fieldName) throws NoSuchFieldException {
        if (claz == null) {
            throw new NoSuchFieldException(fieldName);
        }
        try {
            Field field = claz.getDeclaredField(fieldName);
            return field;
        } catch (NoSuchFieldException e) {
            return findField(claz.getSuperclass(), fieldName);
        }
    }

    public static Object callMethod(Object object, String methodName) {
        Class<?>[] params = new Class[0];
        Object[] paramsObj = new Object[0];
        Class<?> claz = object.getClass();
        Method method;
        Object retobj;
        try {
            method = claz.getMethod(methodName, params);
            retobj = method.invoke(object, paramsObj);
        } catch (SecurityException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return retobj;

    }

}
