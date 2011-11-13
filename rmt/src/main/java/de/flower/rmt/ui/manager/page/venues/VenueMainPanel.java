package de.flower.rmt.ui.manager.page.venues;

import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.manager.page.venues.map.VenuesMapPanel;

/**
 * @author flowerrrr
 */
public class VenueMainPanel extends BasePanel {

    public VenueMainPanel() {
        add(new VenueListPanel());
        add(new VenuesMapPanel());
    }
}
