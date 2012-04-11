package de.flower.rmt.ui.player.page.venues;

import de.flower.rmt.model.Venue;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.model.IModel;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * @author flowerrrr
 */
public class GetDirectionsPanel extends BasePanel {

    public GetDirectionsPanel(final IModel<Venue> model) {
        add(new ExternalLink("link", getLink(model)));
    }

    private String getLink(final IModel<Venue> model) {
        Venue venue = model.getObject();
        DecimalFormat format = new DecimalFormat("##.##############", DecimalFormatSymbols.getInstance(Locale.US));
        String lat = format.format(venue.getLatLng().getLat());
        String lng = format.format(venue.getLatLng().getLng());
        return "http://maps.google.com/maps?daddr=" + lat + "," + lng;
    }
}
