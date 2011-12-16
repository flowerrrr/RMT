package de.flower.rmt.ui.player;

import de.flower.rmt.ui.app.View;
import de.flower.rmt.ui.common.page.INavigationPanelAware;
import de.flower.rmt.ui.common.panel.AbstractNavigationPanel;
import de.flower.rmt.ui.player.page.events.EventsPage;

/**
 * @author flowerrrr
 */
public class NavigationPanel extends AbstractNavigationPanel {

    public static final String EVENTS = "events";

    public NavigationPanel(INavigationPanelAware page) {
        super(View.PLAYER);

        add(createMenuItem(EVENTS, EventsPage.class, page));
    }
}
