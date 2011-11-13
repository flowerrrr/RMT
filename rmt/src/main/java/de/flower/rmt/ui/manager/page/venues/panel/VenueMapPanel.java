package de.flower.rmt.ui.manager.page.venues.panel;

import de.flower.common.util.geo.LatLng;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import wicket.contrib.gmap3.GMap;
import wicket.contrib.gmap3.api.*;

import java.io.Serializable;

/**
 * The model contains the latLng of the marker to display. If null no marker is displayed.
 *
 * @author flowerrrr
 */
public class VenueMapPanel extends BasePanel {

    private GMap map;

    /**
     * @param latLng position of gMarker
     */
    public VenueMapPanel(LatLng latLng) {
        super();

        GMap map = new GMap("map");
        add(map);

        // put draggable marker on map.
        DraggableMarker marker = new DraggableMarker(map, latLng);
        // and center map on marker
        map.setCenter(latLng);

        map.setZoom(14);
    }

    private class DraggableMarker implements Serializable {

        private GMarker gMarker;

        public DraggableMarker(GMap map, GLatLng gLatLng) {
            GMarkerOptions options = new GMarkerOptions(map, gLatLng);
            options = options.draggable(true);
            gMarker = new GMarker(options);
            map.addOverlay(gMarker);
            // add drag listener
            gMarker.addListener(GEvent.dragend, new GEventHandler() {
                @Override
                public void onEvent(AjaxRequestTarget target) {
                    onUpdateMarker(new LatLng(gMarker.getLatLng()));
                }
            });
        }
    }

    /**
     * Called when gMarker on map is set or dragged around.
     *
     * @param latLng
     */
    public void onUpdateMarker(LatLng latLng) {
        ; // empty implementation, subclasses can override
    }
}
