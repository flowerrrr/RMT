package de.flower.rmt.ui.manager.page.venues.panel;

import de.flower.common.util.geo.LatLngEx;
import de.flower.rmt.ui.app.RMTSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;

/**
 * Page wrapper around GMapPanel2 so that panel can be displayed inside iframe.
 * @author oblume
 */
public class GMapPage extends WebPage {

    private GMapPanel2 mapPanel;

    public GMapPage() {

        mapPanel = new GMapPanel2("gmap", RMTSession.get().getLatLng()) {
            @Override
            public void onUpdateMarker(LatLngEx latLng) {
                GMapPage.this.onUpdateMarker(latLng);
            }
        };
        add(mapPanel);

    }

    /**
     * Called when gMarker on map is set or dragged around.
     *
     * @param latLng
     */
    public void onUpdateMarker(LatLngEx latLng) {
        ; // empty implementation, subclasses can override
    }

    public void init(Model<LatLngEx> model) {
        mapPanel.init(model);
    }
}
