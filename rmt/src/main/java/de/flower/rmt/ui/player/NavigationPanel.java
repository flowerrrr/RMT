package de.flower.rmt.ui.player;

import de.flower.rmt.ui.common.page.INavigationPanelAware;
import de.flower.rmt.ui.common.panel.AbstractNavigationPanel;
import de.flower.rmt.ui.player.page.account.AccountPage;
import de.flower.rmt.ui.player.page.events.EventsPage;

/**
 * @author flowerrrr
 */
public class NavigationPanel extends AbstractNavigationPanel {

    public NavigationPanel(String id, INavigationPanelAware page) {
        super(id);

        add(createMenuItem("events", EventsPage.class, page));
        add(createMenuItem("account", AccountPage.class, page));
    }
}
