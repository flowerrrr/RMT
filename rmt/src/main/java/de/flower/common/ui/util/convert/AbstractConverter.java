package de.flower.common.ui.util.convert;

import org.apache.wicket.util.convert.IConverter;

import java.util.Locale;

/**
 * @author flowerrrr
 */
public abstract class AbstractConverter<C> implements IConverter<C> {

    @Override
    public C convertToObject(final String value, final Locale locale) {
        throw new UnsupportedOperationException("Feature not implemented!");
    }
}
