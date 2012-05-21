package de.flower.common.test.wicket;

import org.apache.wicket.Component;
import org.apache.wicket.resource.loader.IStringResourceLoader;

import java.util.Locale;

/**
 * @author flowerrrr
 */
public class MockStringResourceLoader implements IStringResourceLoader {

    @Override
    public String loadStringResource(final Class<?> clazz, final String key, final Locale locale, final String style, final String variation) {
        return "resource-for(" + key + ")";
    }

    @Override
    public String loadStringResource(final Component component, final String key, final Locale locale, final String style, final String variation) {
        return "resource-for(" + key + ")";
    }
}

