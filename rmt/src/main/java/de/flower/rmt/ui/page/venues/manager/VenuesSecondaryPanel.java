package de.flower.rmt.ui.page.venues.manager;

import de.flower.rmt.ui.panel.BasePanel;
import org.apache.wicket.markup.html.link.Link;

/**
 * @author flowerrrr
 */
public class VenuesSecondaryPanel extends BasePanel {

    public VenuesSecondaryPanel() {
        add(new Link("newButton") {

            @Override
            public void onClick() {
                setResponsePage(new VenueEditPage());
            }
        });
    }
}
