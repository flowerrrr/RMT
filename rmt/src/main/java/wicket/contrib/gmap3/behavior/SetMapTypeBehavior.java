package wicket.contrib.gmap3.behavior;

import com.bosch.cbs.ui.web.common.map.gmap3.GMap;
import com.bosch.cbs.ui.web.common.map.gmap3.ReviewPending;
import wicket.contrib.gmap3.api.GMapType;

/**
 * The Class SetMapTypeBehavior.
 */
@ReviewPending
// remove when class is tested
public class SetMapTypeBehavior extends JSMethodBehavior {
    /**
     *
     */
    private final GMap gMap;

    private static final long serialVersionUID = 1L;

    private final GMapType _mapTypeBehavior;

    /**
     * Instantiates a new sets the map type behavior.
     *
     * @param event the event
     * @param mapType the map type
     * @param gMap the GMap instance
     */
    public SetMapTypeBehavior(final GMap gMap, final String event, final GMapType mapType) {
        super(event);
        this.gMap = gMap;
        _mapTypeBehavior = mapType;
    }

    @Override
    protected String getJSinvoke() {
        return _mapTypeBehavior.getJSsetMapType(gMap);
    }
}