package de.flower.rmt.ui.manager;

import de.flower.rmt.ui.app.View;
import de.flower.rmt.ui.common.page.INavigationPanelAware;
import de.flower.rmt.ui.common.panel.AbstractNavigationPanel;
import de.flower.rmt.ui.manager.page.events.EventsPage;
import de.flower.rmt.ui.manager.page.opponents.OpponentsPage;
import de.flower.rmt.ui.manager.page.players.PlayersPage;
import de.flower.rmt.ui.manager.page.teams.TeamsPage;
import de.flower.rmt.ui.manager.page.venues.VenuesPage;

/**
 * @author flowerrrr
 */
public class NavigationPanel extends AbstractNavigationPanel {

    public NavigationPanel(INavigationPanelAware page) {
        super(View.MANAGER);

        add(createMenuItem("home", ManagerHomePage.class, page));
        add(createMenuItem("events", EventsPage.class, page));
        add(createMenuItem("teams", TeamsPage.class, page));
        add(createMenuItem("players", PlayersPage.class, page));
        add(createMenuItem("opponents", OpponentsPage.class, page));
        add(createMenuItem("venues", VenuesPage.class, page));

    }

}
