package de.flower.rmt.ui.page.base.player;

import de.flower.rmt.ui.app.View;
import de.flower.rmt.ui.page.base.INavigationPanelAware;
import de.flower.rmt.ui.page.events.player.EventsPage;
import de.flower.rmt.ui.page.venues.player.VenuesPage;
import de.flower.rmt.ui.panel.AbstractNavigationPanel;

/**
 * @author flowerrrr
 */
public class NavigationPanel extends AbstractNavigationPanel {

    public static final String EVENTS = "events";

    public static final String VENUES = "venues";

    public NavigationPanel(INavigationPanelAware page) {
        super(View.PLAYER);

        add(createMenuItem(EVENTS, EventsPage.class, page));
        add(createMenuItem(VENUES, VenuesPage.class, page));
    }
}
