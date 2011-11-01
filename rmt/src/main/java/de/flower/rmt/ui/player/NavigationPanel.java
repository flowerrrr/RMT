package de.flower.rmt.ui.player;

import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.common.panel.LogoutLink;
import de.flower.rmt.ui.player.page.events.EventsPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

/**
 * @author flowerrrr
 */
public class NavigationPanel extends BasePanel {

    public NavigationPanel(String id) {
        super(id);

        add(new BookmarkablePageLink("events", EventsPage.class));

        add(new LogoutLink("logoutLink"));
        add(new Label("user", getUser().getFullname()));
    }
}
