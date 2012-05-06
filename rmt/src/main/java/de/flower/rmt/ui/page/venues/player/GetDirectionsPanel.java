package de.flower.rmt.ui.page.venues.player;

import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.Venue;
import de.flower.rmt.ui.app.Links;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class GetDirectionsPanel extends BasePanel {

    public GetDirectionsPanel(final IModel<Venue> model) {
        add(new ExternalLink("link", Links.getDirectionsUrl(model.getObject().getLatLng())));
    }

}
