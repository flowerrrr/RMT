package de.flower.rmt.ui.app;

import org.apache.wicket.Session;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author flowerrrr
 */
public class ExceptionRequestCycleListener extends AbstractRequestCycleListener {

    private final static Logger log = LoggerFactory.getLogger(ExceptionRequestCycleListener.class);

    @Override
    public IRequestHandler onException(final RequestCycle cycle, final Exception ex) {
        // only save the exception in the session so that our internal error page can access it.
        // saves us from completely rewriting exception handling.
        Session.get().getFeedbackMessages().error(null, ex);
        // let wicket decide how to deal with this exception
        return null;
    }
}
