package de.flower.rmt.ui.manager.page.venues;

import de.flower.rmt.ui.manager.ManagerBasePage;
import de.flower.rmt.ui.manager.page.venues.panel.VenuesMapPanel;

/**
 * @author flowerrrr
 */
public class VenuesPage extends ManagerBasePage {

     public VenuesPage() {

        add(new VenueListPanel());
        add(new VenuesMapPanel());
    }

    @Override
    public String getActiveTopBarItem() {
        return "venues";
    }

}
