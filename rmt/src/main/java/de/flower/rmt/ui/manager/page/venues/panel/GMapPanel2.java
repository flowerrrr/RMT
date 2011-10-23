package de.flower.rmt.ui.manager.page.venues.panel;

import de.flower.common.util.geo.LatLngEx;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import wicket.contrib.gmap3.GMap;
import wicket.contrib.gmap3.api.*;
import wicket.contrib.gmap3.event.ClickListener;

import java.io.Serializable;

/**
 * The model contains the latLng of the marker to display. If null no marker is displayed.
 *
 * @author flowerrrr
 */
public class GMapPanel2 extends BasePanel {

    private GMap mapNew;

    private GMap mapEdit;

    /**
     * @param id
     * @param latLng position of gMarker
     */
    public GMapPanel2(String id, LatLngEx defaultCenter) {
        super(id);

        mapNew = initMap("mapNew");
        mapNew.setCenter(defaultCenter);
        mapNew.add(new ClickListener() {
             @Override
             protected void onClick(AjaxRequestTarget target, LatLng glatLng, GOverlay overlay) {
                 if (glatLng != null) {
                     // only one gMarker allowed. if already a gMarker present ignore click event.
                     if (mapNew.getOverlays().size() == 0) {
                         DraggableMarker marker = new DraggableMarker(mapNew, glatLng);
                         onUpdateMarker(new LatLngEx(glatLng));
                     }
                 }
             }
        });

        mapEdit = initMap("mapEdit");
    }

    private GMap initMap(String id) {
        GMap map = new GMap(id);
        add(map);

        map.setZoom(14);
        return map;
    }

    /**
     * Update map marker.
     */
    public void init(IModel<LatLngEx> model) {
        LatLngEx latLng = model.getObject();
        if (latLng == null) {
            mapNew.setVisible(true);
            mapEdit.setVisible(false);
            mapNew.removeAllOverlays();
        } else {
            mapNew.setVisible(false);
            mapEdit.setVisible(true);
            mapEdit.removeAllOverlays();
            // put draggable marker on map.
            DraggableMarker marker = new DraggableMarker(mapEdit, latLng);
            // and center map on marker
            mapEdit.setCenter(latLng);
        }

    }

    public GMap getGMap() {
        return mapNew.isVisible() ? mapNew : mapEdit;
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
