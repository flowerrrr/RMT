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

    public static final String HOME = "home";

    public static final String EVENTS = "events";

    public static final String TEAMS = "teams";

    public static final String PLAYERS = "players";

    public static final String OPPONENTS = "opponents";

    public static final String VENUES = "venues";

    public NavigationPanel(INavigationPanelAware page) {
        super(View.MANAGER);

//        add(createMenuItem(HOME, ManagerHomePage.class, page));
        add(createMenuItem(EVENTS, EventsPage.class, page));
        add(createMenuItem(TEAMS, TeamsPage.class, page));
        add(createMenuItem(PLAYERS, PlayersPage.class, page));
        add(createMenuItem(OPPONENTS, OpponentsPage.class, page));
        add(createMenuItem(VENUES, VenuesPage.class, page));

    }

}
