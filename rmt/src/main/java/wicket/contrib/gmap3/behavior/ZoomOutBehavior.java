package wicket.contrib.gmap3.behavior;

import wicket.contrib.gmap3.GMap;
import wicket.contrib.gmap3.ReviewPending;


/**
 * The Class ZoomOutBehavior.
 */
@ReviewPending
// remove when class is tested
public class ZoomOutBehavior extends JSMethodBehavior {
    /**
     *
     */
    private final GMap gMap;
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new zoom out behavior.
     *
     * @param event the event
     * @param gMap the GMap instance
     */
    public ZoomOutBehavior(final GMap gMap, final String event) {
        super(event);
        this.gMap = gMap;
    }

    @Override
    protected String getJSinvoke() {
        return gMap.getJSzoomOut();
    }
}