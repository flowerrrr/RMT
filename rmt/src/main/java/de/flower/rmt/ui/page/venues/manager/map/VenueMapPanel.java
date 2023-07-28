package de.flower.rmt.ui.page.venues.manager.map;

import de.flower.common.ui.panel.BasePanel;
import de.flower.common.util.geo.LatLng;
import de.flower.rmt.model.db.entity.Venue;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.string.Strings;
import wicket.contrib.gmap3.GMap;
import wicket.contrib.gmap3.overlay.GInfoWindow;
import wicket.contrib.gmap3.overlay.GMarker;
import wicket.contrib.gmap3.overlay.GMarkerOptions;
import wicket.contrib.gmap3.overlay.GOverlayEvent;
import wicket.contrib.gmap3.overlay.GOverlayEventHandler;

/**
 * The model contains the latLng of the marker to display. If null no marker is displayed.
 */
public class VenueMapPanel extends BasePanel<LatLng> {

    private GMap map;

    private GMarkerOptions options;

    public VenueMapPanel(IModel<LatLng> model) {
        this(model, null, true);
    }

    public VenueMapPanel(final IModel<LatLng> model, final String infoWindowContent) {
        this(model, infoWindowContent, false);
    }

    /**
     * @param latLng position of gMarker
     */
    private VenueMapPanel(IModel<LatLng> model, String infoWindowContent, boolean draggableMarker) {
        super(model);

        LatLng latLng = model.getObject();
        if (latLng == null) {
            // init with 0,0
            latLng = new LatLng(0, 0);
            model.setObject(latLng);
        }
        map = new GMap("map");
        add(map);
        map.setDoubleClickZoomEnabled(true);

        if (draggableMarker) {
            // put draggable marker on map.
            options = new GMarkerOptions(map, latLng);
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
        } else {
            options = new GMarkerOptions(map, latLng);
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

    /**
     * Called when gMarker on map is set or dragged around.
     *
     * @param latLng
     */
    public void onUpdateMarker(LatLng latLng) {
        //  subclasses can override
        setModelObject(latLng);
    }

    @Override
    protected void onBeforeRender() {
        // update settings if latLng has changed, should be enough to reflect model changes when panel is repainted.
        map.setCenter(getModelObject());
        options.setLatLng(getModelObject());
        super.onBeforeRender();
    }

    public static String getInfoWindowContent(final Venue venue, final String url) {
        String s = "<div><ul class='info-window'>" +
                "<li>" +
                "<span class='name'>" + venue.getName() + "</span>" +
                "</li>" +
                "<li>" +
                "<span>" + (venue.getAddress() != null ? Strings.toMultilineMarkup(venue.getAddress()) : "") + "</span>" +
                "</li>" +
                "<li>" +
                "<span><a href='" + url + "' target='_blank' class='btn-link-external'>" +
                new ResourceModel("venue.getdirections.link").getObject() + "</a></span>" +
                "</li>" +
                "</ul></div>";
        return s;
    }
}
