package de.flower.rmt.ui.manager.page.venues;

import de.flower.rmt.ui.common.panel.BasePanel;
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
