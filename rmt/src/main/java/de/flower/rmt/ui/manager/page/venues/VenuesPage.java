package de.flower.rmt.ui.manager.page.venues;

import de.flower.rmt.ui.manager.ManagerBasePage;
import de.flower.rmt.ui.manager.page.venues.panel.GMapPanel;

/**
 * @author flowerrrr
 */
public class VenuesPage extends ManagerBasePage {

     public VenuesPage() {

        add(new VenueListPanel("venueListPanel"));
        add(new GMapPanel("mapPanel"));
    }


}
