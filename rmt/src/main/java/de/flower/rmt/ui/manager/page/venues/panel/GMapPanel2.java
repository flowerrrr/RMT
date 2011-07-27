package de.flower.rmt.ui.manager.page.venues.panel;

import de.flower.common.util.geo.LatLngEx;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import wicket.contrib.gmap3.GMap;
import wicket.contrib.gmap3.api.*;
import wicket.contrib.gmap3.event.ClickListener;

import java.io.Serializable;

/**
 * The model contains the latLng of the marker to display. If null no marker is displayed.
 *
 * @author oblume
 */
public class GMapPanel2 extends Panel {

    private GMap map;

    /**
     * @param id
     * @param latLng position of gMarker
     */
    public GMapPanel2(String id, LatLngEx defaultCenter) {
        super(id);

        map = new GMap("map");
        add(map);

        map.setCenter(defaultCenter);
        map.setZoom(10);
    }

    /**
     * Update map marker.
     */
    public void init(IModel<LatLngEx> model) {
        resetMap();
        LatLngEx latLng = model.getObject();
        if (latLng == null) {
            // panel in new-mode, no marker present, but can be added by clicking on map.
            map.add(new ClickListener() {
                @Override
                protected void onClick(AjaxRequestTarget target, LatLng glatLng, GOverlay overlay) {
                    if (glatLng != null) {
                        // only one gMarker allowed. if already a gMarker present ignore click event.
                        if (map.getOverlays().size() == 0) {
                            DraggableMarker marker = new DraggableMarker(map, glatLng);
                            onUpdateMarker(new LatLngEx(glatLng));
                        }
                    }
                }
            });
        } else {
            // put draggable marker on map.
            DraggableMarker marker = new DraggableMarker(map, latLng);
            // and center map on marker
            map.setCenter(latLng);
        }
    }

    private void resetMap() {
        map.removeAllOverlays();
    }

    private class DraggableMarker implements Serializable {

        private GMarker gMarker;

        public DraggableMarker(GMap map, LatLng gLatLng) {
            GMarkerOptions options = new GMarkerOptions(map, gLatLng);
            options = options.draggable(true);
            gMarker = new GMarker(options);
            map.addOverlay(gMarker);
            // add drag listener
            gMarker.addListener(GEvent.dragend, new GEventHandler() {
                @Override
                public void onEvent(AjaxRequestTarget target) {
                    onUpdateMarker(new LatLngEx(gMarker.getLatLng()));
                }
            });
        }

    }

    /**
     * Called when gMarker on map is set or dragged around.
     *
     * @param latLng
     */
    public void onUpdateMarker(LatLngEx latLng) {
        ; // empty implementation, subclasses can override
    }
}
