package de.flower.rmt.ui.manager.page.venues.panel;

import de.flower.common.util.geo.LatLng;
import de.flower.rmt.service.IVenueManager;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import wicket.contrib.gmap3.GMap;

/**
 * @author oblume
 */
public class GMapPanel2 extends Panel {

    @SpringBean
    private IVenueManager venueManager;

    /**
     *
     * @param id
     * @param latLng position of marker
     */
    public GMapPanel2(String id, LatLng marker, LatLng defaultCenter) {
        super(id);

        GMap map = new GMap("map");
        add(map);

        map.setCenter((marker != null) ? marker : defaultCenter);
        map.setZoom(10);


    }

    /**
     * Called when marker on map is set or dragged around.
     * @param latLng
     */
    public void onUpdateMarker(LatLng latLng) {
        ; // empty implementation, subclasses can override
    }
}
