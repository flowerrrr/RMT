package de.flower.rmt.ui.manager;

import de.flower.rmt.ui.manager.page.players.PlayersPage;
import de.flower.rmt.ui.manager.page.teams.TeamsPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @author oblume
 */
public class NavigationPanel extends Panel {

    public NavigationPanel(String id) {
        super(id);

        add(new BookmarkablePageLink("home", ManagerHomePage.class));
        add(new BookmarkablePageLink("teams", TeamsPage.class));
        add(new BookmarkablePageLink("players", PlayersPage.class));
    }
}
