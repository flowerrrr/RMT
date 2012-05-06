package de.flower.rmt.ui.page.venues.player;

import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.Venue;
import de.flower.rmt.ui.page.venues.manager.map.VenueMapPanel;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.Strings;

/**
 * @author flowerrrr
 */
public class VenuePanel extends BasePanel {

    public VenuePanel(final IModel<Venue> model) {
        setDefaultModel(new CompoundPropertyModel<Venue>(model));

        add(new Label("name"));

        add(new Label("address") {

            @Override
            public void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag) {
                replaceComponentTagBody(markupStream, openTag, Strings.toMultilineMarkup(getDefaultModelObjectAsString()));
            }
        });

        add(new VenueMapPanel(model.getObject().getLatLng(), VenueMapPanel.getInfoWindowContent(model.getObject())));
    }

}
