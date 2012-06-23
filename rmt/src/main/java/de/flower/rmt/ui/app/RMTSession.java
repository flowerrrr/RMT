package de.flower.rmt.ui.app;

import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

/**
 * @author flowerrrr
 */
public class RMTSession extends WebSession {

    private View view;

    /**
     * Constructor. Note that {@link org.apache.wicket.request.cycle.RequestCycle} is not available until this constructor returns.
     *
     * @param request The current request
     */
    public RMTSession(Request request) {
        super(request);
    }

    public static RMTSession get() {
        return (RMTSession) WebSession.get();
    }

    public View getView() {
        return view;
    }

    public void setView(final View view) {
        this.view = view;
    }
}
