package de.flower.rmt.ui.page.venues.manager.map;

import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.Venue;
import de.flower.rmt.service.IUrlProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class VenuesMapPanel extends BasePanel {

    /** Google maps maximum zoom level in map mode is 19 (22 in satellite). */
    public static final int MAX_INITAL_ZOOM_LEVEL = 15;

    @SpringBean(name = "urlProvider")
    private IUrlProvider urlProvider;

    public VenuesMapPanel(IModel<List<Venue>> listModel) {

/*
    Google Maps API hat sich geändert, Code funktioniert nicht mehr.

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
                marker.addFunctionListener(GOverlayEvent.CLICK, GInfoWindow.getJSopenFunction(map, VenueMapPanel.getInfoWindowContent(venue, urlProvider.getDirectionsUrl(venue.getLatLng())), marker));
                latLngs.add(venue.getLatLng());
            }
        }

        // LatLng center = GeoUtil.centerOf(latLngs);
        // map.setCenter(center);
        map.fitMarkers(latLngs, MAX_INITAL_ZOOM_LEVEL);

        // updates of venue locations must be done via javascript. repainting the whole map
        // takes to long and doesn't look nice.
        // add(new AjaxUpdateBehavior(Event.EntityAll(Venue.class)));
*/
    }

}
