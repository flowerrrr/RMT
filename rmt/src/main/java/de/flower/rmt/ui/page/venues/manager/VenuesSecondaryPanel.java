package de.flower.rmt.ui.page.venues.manager;

import de.flower.common.ui.panel.BasePanel;
import org.apache.wicket.markup.html.link.Link;


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
