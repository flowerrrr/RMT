package de.flower.rmt.ui.page.venues.player;

import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.Venue;
import de.flower.rmt.service.IUrlProvider;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.Strings;


public class VenuePanel extends BasePanel {

    @SpringBean(name = "urlProvider")
    private IUrlProvider urlProvider;

    public VenuePanel(final IModel<Venue> model) {
        setDefaultModel(new CompoundPropertyModel<Venue>(model));

        add(new Label("name"));

        add(new Label("address") {

            @Override
            public void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag) {
                replaceComponentTagBody(markupStream, openTag, Strings.toMultilineMarkup(getDefaultModelObjectAsString()));
            }
        });

        // add(new VenueMapPanel(new PropertyModel<LatLng>(model, "latLng"), VenueMapPanel.getInfoWindowContent(model.getObject(), urlProvider.getDirectionsUrl(model.getObject().getLatLng()))));
    }

}
