package de.flower.common.ui.util.convert;

import org.apache.wicket.util.string.Strings;

import java.util.Locale;

/**
 * NOTE: Requires #setEscapeModelString(false) set on label.
 * @author flowerrrr
 */
public class MultiLineConverter extends AbstractConverter<String> {

    @Override
    public String convertToString(final String value, final Locale locale) {
        return Strings.toMultilineMarkup(value).toString();
    }
}
