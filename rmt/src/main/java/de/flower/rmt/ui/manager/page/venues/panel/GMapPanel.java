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
import wicket.contrib.gmap3.api.GOverlay;

import java.util.ArrayList;
import java.util.List;

/**
 * @author oblume
 */
public class GMapPanel extends Panel {

    @SpringBean
    private IVenueManager venueManager;

    public GMapPanel(String id) {
        super(id);

        GMap map = new GMap("map");
        add(map);

        List<LatLngEx> latLngs = new ArrayList<LatLngEx>();
        for (Venue venue : venueManager.findAll()) {
            if (venue.getLatLng() != null) {
                GOverlay marker = new GMarker(new GMarkerOptions(map,
                        venue.getLatLng(),
                        venue.getName()));
                map.addOverlay(marker);
                latLngs.add(venue.getLatLng());
            }
        }

        LatLngEx center = GeoUtil.centerOf(latLngs);
        map.setCenter(center);
        map.setZoom(10);


    }
}
