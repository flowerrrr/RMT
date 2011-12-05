package wicket.contrib.gmap3.behavior;

import wicket.contrib.gmap3.GMap;
import wicket.contrib.gmap3.ReviewPending;


/**
 * The Class PanDirectionBehavior.
 */
@ReviewPending
// remove when class is tested
public class PanDirectionBehavior extends JSMethodBehavior {
    /**
     *
     */
    private final GMap gMap;

    private static final long serialVersionUID = 1L;

    private final int _dx;

    private final int _dy;

    /**
     * Instantiates a new pan direction behavior.
     *
     * @param event the event
     * @param dx the dx
     * @param dy the dy
     * @param gMap the GMap instance
     */
    public PanDirectionBehavior(final GMap gMap, final String event, final int dx, final int dy) {
        super(event);
        this.gMap = gMap;
        _dx = dx;
        _dy = dy;
    }

    @Override
    protected String getJSinvoke() {
        return gMap.getJSpanDirection(_dx, _dy);
    }
}