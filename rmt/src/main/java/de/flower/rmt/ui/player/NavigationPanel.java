package de.flower.rmt.ui.player;

import de.flower.rmt.ui.app.View;
import de.flower.rmt.ui.common.page.INavigationPanelAware;
import de.flower.rmt.ui.common.panel.AbstractNavigationPanel;
import de.flower.rmt.ui.player.page.events.EventsPage;
import de.flower.rmt.ui.player.page.venues.VenuesPage;

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
