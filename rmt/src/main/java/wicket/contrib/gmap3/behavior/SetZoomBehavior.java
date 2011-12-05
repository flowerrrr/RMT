package wicket.contrib.gmap3.behavior;

import wicket.contrib.gmap3.GMap;
import wicket.contrib.gmap3.ReviewPending;


/**
 * The Class SetZoomBehavior.
 */
@ReviewPending
// remove when class is tested
public class SetZoomBehavior extends JSMethodBehavior {
    /**
     *
     */
    private final GMap gMap;

    private static final long serialVersionUID = 1L;

    private final int _zoomBehavior;

    /**
     * Instantiates a new sets the zoom behavior.
     *
     * @param event the event
     * @param zoom the zoom
     * @param gMap the GMap instance
     */
    public SetZoomBehavior(final GMap gMap, final String event, final int zoom) {
        super(event);
        this.gMap = gMap;
        _zoomBehavior = zoom;
    }

    @Override
    protected String getJSinvoke() {
        return gMap.getJSsetZoom(_zoomBehavior);
    }
}