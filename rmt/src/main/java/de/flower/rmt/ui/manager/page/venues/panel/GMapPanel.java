package de.flower.rmt.ui.manager.page.venues.panel;

import de.flower.common.util.geo.GeoUtil;
import de.flower.common.util.geo.LatLngEx;
import de.flower.rmt.model.Venue;
import de.flower.rmt.service.IVenueManager;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import wicket.contrib.gmap3.GMap;
import wicket.contrib.gmap3.api.GMarker;
import wicket.contrib.gmap3.api.GMarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author flowerrrr
 */
public class GMapPanel extends Panel {

    @SpringBean
    private IVenueManager venueManager;

    public GMapPanel(String id) {
        super(id);

        final GMap map = new GMap("map");
        add(map);

        List<LatLngEx> latLngs = new ArrayList<LatLngEx>();
        for (final Venue venue : venueManager.findAll()) {
            if (venue.getLatLng() != null) {
                final GMarker marker = new GMarker(new GMarkerOptions(map,
                        venue.getLatLng(),
                        venue.getName()));
                map.addOverlay(marker);
                latLngs.add(venue.getLatLng());
            }
        }

        LatLngEx center = GeoUtil.centerOf(latLngs);
        map.setCenter(center);
        map.setZoom(10);

        // updates of venue locations must be done via javascript. repainting the whole map
        // takes to long and doesn't look nice.
        // add(new AjaxUpdateBehavior(Event.EntityAll(Venue.class)));
    }
}
