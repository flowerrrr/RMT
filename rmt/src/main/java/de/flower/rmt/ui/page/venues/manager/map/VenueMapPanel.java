package de.flower.rmt.ui.page.venues.manager.map;

import de.flower.common.util.geo.LatLng;
import de.flower.rmt.model.Venue;
import de.flower.rmt.ui.app.Links;
import de.flower.rmt.ui.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.string.Strings;
import wicket.contrib.gmap3.GMap;
import wicket.contrib.gmap3.api.GLatLng;
import wicket.contrib.gmap3.overlay.*;

import java.io.Serializable;

/**
 * The model contains the latLng of the marker to display. If null no marker is displayed.
 *
 * @author flowerrrr
 */
public class VenueMapPanel extends BasePanel {

    public VenueMapPanel(LatLng latLng, boolean draggableMarker) {
        this(latLng, null, draggableMarker);
    }

    /**
     * @param latLng position of gMarker
     */
    public VenueMapPanel(LatLng latLng, String infoWindowContent, boolean draggableMarker) {
        super();

        GMap map = new GMap("map");
        add(map);
        map.setDoubleClickZoomEnabled(true);

        if (draggableMarker) {
            // put draggable marker on map.
            DraggableMarker marker = new DraggableMarker(map, latLng);
        } else {
            GMarkerOptions options = new GMarkerOptions(map, latLng);
            GMarker gMarker = new GMarker(options);
            map.addOverlay(gMarker);
            if (infoWindowContent != null) {
                gMarker.addFunctionListener(GOverlayEvent.CLICK, GInfoWindow.getJSopenFunction(map, infoWindowContent, gMarker));
            }
        }
        // and center map on marker
        map.setCenter(latLng);

        map.setZoom(14);
    }

    private class DraggableMarker implements Serializable {

        public DraggableMarker(GMap map, GLatLng gLatLng) {
            GMarkerOptions options = new GMarkerOptions(map, gLatLng);
            options = options.draggable(true);
            final GMarker gMarker = new GMarker(options);
            map.addOverlay(gMarker);
            // add drag listener
            gMarker.addListener(GOverlayEvent.DRAGEND, new GOverlayEventHandler() {
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
        // empty implementation, subclasses can override
    }

    public static String getInfoWindowContent(final Venue venue) {
        String s = "<div><ul class='info-window'>" +
                "<li>" +
                "<span class='name'>" + venue.getName() + "</span>" +
                "</li>" +
                "<li>" +
                "<span>" + (venue.getAddress() != null ? Strings.toMultilineMarkup(venue.getAddress()) : "") + "</span>" +
                "</li>" +
                "<li>" +
                "<span><a href='" + Links.getDirectionsUrl(venue.getLatLng()) + "' target='_blank' class='btn-link-external'>" +
                new ResourceModel("venue.getdirections.link").getObject() + "</a></span>" +
                "</li>" +
                "</ul></div>";
        return s;
    }

}
