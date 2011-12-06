package de.flower.rmt.ui.manager.page.venues.map;

import de.flower.common.util.geo.GeoUtil;
import de.flower.common.util.geo.LatLng;
import de.flower.rmt.model.Venue;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.model.IModel;
import wicket.contrib.gmap3.GMap;
import wicket.contrib.gmap3.overlay.GMarker;
import wicket.contrib.gmap3.overlay.GMarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author flowerrrr
 */
public class VenuesMapPanel extends BasePanel {

    public VenuesMapPanel(IModel<List<Venue>> listModel) {

        final GMap map = new GMap("map");
        add(map);
        map.setDoubleClickZoomEnabled(true);

        // NOTE (flowerrrr - 06.12.11) move this code to onbeforeRender if ajax-updates should work
        List<LatLng> latLngs = new ArrayList<LatLng>();
        for (final Venue venue : listModel.getObject()) {
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
