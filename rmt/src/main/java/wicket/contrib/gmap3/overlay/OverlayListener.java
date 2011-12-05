package wicket.contrib.gmap3.overlay;

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wicket.contrib.gmap3.GMap;
import wicket.contrib.gmap3.ReviewPending;

@ReviewPending
// remove when class is tested
public class OverlayListener extends AbstractDefaultAjaxBehavior {

    private static final Logger LOG = LoggerFactory.getLogger(OverlayListener.class);

    @Override
    protected void onBind() {
        if ( !( getComponent() instanceof GMap ) ) {
            throw new IllegalArgumentException( "must be bound to GMap" );
        }
    }

    private static final long serialVersionUID = 1L;

    /**
     * This method is called by event listeners that are registered with WicketMap.addOverlayListener.
     * Look at this method if you need to add more parameters.
     */
    @Override
    protected void respond(final AjaxRequestTarget target) {
        final Request request = RequestCycle.get().getRequest();

        final String overlayId = request.getRequestParameters().getParameterValue("overlay.overlayId").toString();
        final String event = request.getRequestParameters().getParameterValue("overlay.event").toString();

        LOG.debug("OverlayListener.respond(event [{}], overlayId [{}]", event, overlayId);
        getGMap().update();

        // final String latLng = request.getRequestParameters().getParameterValue("overlay.latLng").toString();
        // TODO (oliverb - ?): this is ugly
        // the id's of the Overlays are unique within the ArrayList
        // maybe we should change that collection
        for (final GOverlay overlay : getGMap().getOverlays()) {
            if (overlay.getId().equals(overlayId)) {
                overlay.onEvent(target, GOverlayEvent.from(event));
                break;
            }
        }
    }

    public String getJSinit() {
        return getGMap().getJSinvoke("overlayListenerCallbackUrl = '" + getCallbackUrl() + "'");
    }

    protected final GMap getGMap() {
        return (GMap) getComponent();
    }


}