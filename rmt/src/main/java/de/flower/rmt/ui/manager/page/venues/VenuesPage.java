package de.flower.rmt.ui.manager.page.venues;

import de.flower.rmt.ui.manager.ManagerBasePage;
import de.flower.rmt.ui.manager.NavigationPanel;

/**
 * @author flowerrrr
 */
public class VenuesPage extends ManagerBasePage {

    public VenuesPage() {

        setHeading("manager.venues.heading", null);
        addMainPanel(new VenueMainPanel());
        addSecondaryPanel(new VenuesSecondaryPanel());
    }

    @Override
    public String getActiveTopBarItem() {
        return NavigationPanel.VENUES;
    }
}
