package de.flower.rmt.ui.manager;

import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.manager.page.events.EventsPage;
import de.flower.rmt.ui.manager.page.players.PlayersPage;
import de.flower.rmt.ui.manager.page.teams.TeamsPage;
import de.flower.rmt.ui.manager.page.venues.VenuesPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

/**
 * @author flowerrrr
 */
public class NavigationPanel extends BasePanel {

    public NavigationPanel(String id) {
        super(id);

        add(new BookmarkablePageLink("home", ManagerHomePage.class));
        add(new BookmarkablePageLink("events", EventsPage.class));
        add(new BookmarkablePageLink("teams", TeamsPage.class));
        add(new BookmarkablePageLink("players", PlayersPage.class));
        add(new BookmarkablePageLink("venues", VenuesPage.class));
    }
}
