package de.flower.rmt.ui.page.venues.manager;

import de.flower.rmt.ui.page.Pages;
import de.flower.rmt.ui.page.base.manager.ManagerBasePage;

/**
 * @author flowerrrr
 */
public class VenuesPage extends ManagerBasePage {

    public VenuesPage() {

        setHeading("manager.venues.heading", null);
        addMainPanel(new VenuesMainPanel());
        addSecondaryPanel(new VenuesSecondaryPanel());
    }

    @Override
    public String getActiveTopBarItem() {
        return Pages.VENUES.name();
    }
}
