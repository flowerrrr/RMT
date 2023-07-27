package de.flower.rmt.ui.page.venues.player;

import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.Venue;
import de.flower.rmt.service.IUrlProvider;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class GetDirectionsPanel extends BasePanel<Venue> {

    @SpringBean(name = "urlProvider")
    private IUrlProvider urlProvider;

    public GetDirectionsPanel(final IModel<Venue> model) {
        super(model);
        String directionsUrl = model.getObject().getLatLng() != null ? urlProvider.getDirectionsUrl(model.getObject().getLatLng()) : null;
        add(new ExternalLink("link", directionsUrl));
    }

    @Override
    public boolean isVisible() {
        return getModelObject().getLatLng() != null;
    }

}
