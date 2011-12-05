package wicket.contrib.gmap3.behavior;

import com.bosch.cbs.ui.web.common.map.gmap3.GMap;
import com.bosch.cbs.ui.web.common.map.gmap3.ReviewPending;
import wicket.contrib.gmap3.api.GLatLng;

/**
 * The Class SetCenterBehavior.
 */
@ReviewPending
// remove when class is tested
public class SetCenterBehavior extends JSMethodBehavior {
    /**
     *
     */
    private final GMap gMap;

    private static final long serialVersionUID = 1L;

    private final GLatLng _gLatLng;

    /**
     * Instantiates a new sets the center behavior.
     *
     * @param event the event
     * @param gLatLng the g lat lng
     * @param gMap the GMap instance
     */
    public SetCenterBehavior(final GMap gMap, final String event, final GLatLng gLatLng) {
        super(event);
        this.gMap = gMap;
        _gLatLng = gLatLng;
    }

    @Override
    protected String getJSinvoke() {
        return gMap.getJSsetCenter(_gLatLng);
    }
}