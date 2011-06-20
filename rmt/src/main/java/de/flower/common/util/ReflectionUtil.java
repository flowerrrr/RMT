package de.flower.common.util;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author oblume
 */
public class ReflectionUtil {


    public static Object getProperty(Object bean, String name) {
        try {
            return PropertyUtils.getProperty(bean, name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
