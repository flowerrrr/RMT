package de.flower.rmt.ui.manager.page.venues;

import de.flower.rmt.ui.manager.ManagerBasePage;

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
        return "venues";
    }
}
