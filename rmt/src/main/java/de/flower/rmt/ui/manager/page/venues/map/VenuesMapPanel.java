package de.flower.rmt.ui.manager.page.venues.map;

import de.flower.common.util.geo.GeoUtil;
import de.flower.common.util.geo.LatLng;
import de.flower.rmt.model.Venue;
import de.flower.rmt.service.IVenueManager;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import wicket.contrib.gmap3.GMap;
import wicket.contrib.gmap3.overlay.GMarker;
import wicket.contrib.gmap3.overlay.GMarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author flowerrrr
 */
public class VenuesMapPanel extends BasePanel {

    @SpringBean
    private IVenueManager venueManager;

    public VenuesMapPanel() {
        super();

        final GMap map = new GMap("map");
        add(map);
        map.setDoubleClickZoomEnabled(true);

        List<LatLng> latLngs = new ArrayList<LatLng>();
        for (final Venue venue : venueManager.findAll()) {
            if (venue.getLatLng() != null) {
                final GMarker marker = new GMarker(new GMarkerOptions(map,
                        venue.getLatLng(),
                        venue.getName()));
                map.addOverlay(marker);
                latLngs.add(venue.getLatLng());
            }
        }

        LatLng center = GeoUtil.centerOf(latLngs);
        map.setCenter(center);
        map.setZoom(10);

        // updates of venue locations must be done via javascript. repainting the whole map
        // takes to long and doesn't look nice.
        // add(new AjaxUpdateBehavior(Event.EntityAll(Venue.class)));
    }
}
