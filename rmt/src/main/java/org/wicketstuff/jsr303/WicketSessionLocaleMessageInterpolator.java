package org.wicketstuff.jsr303;

import com.google.common.base.Preconditions;
import org.apache.wicket.Session;

import javax.validation.MessageInterpolator;
import java.util.Locale;

 
public class WicketSessionLocaleMessageInterpolator implements MessageInterpolator {

    private final MessageInterpolator delegate;

    public WicketSessionLocaleMessageInterpolator(final MessageInterpolator delegate) {
        this.delegate = delegate;
        Preconditions.checkNotNull(delegate, "delegate");
    }

    public String interpolate(final String message, final Context context) {
        return delegate.interpolate(message, context, Session.get().getLocale());
    }

    public String interpolate(final String message, final Context context, final Locale locale) {
        return delegate.interpolate(message, context, Session.get().getLocale());
    }
}
