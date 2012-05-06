package de.flower.rmt.ui.page.users.manager;

import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.ui.page.user.manager.PlayerPage;
import org.apache.wicket.markup.html.link.Link;

/**
 * @author flowerrrr
 */
public class PlayersSecondaryPanel extends BasePanel {

    public PlayersSecondaryPanel() {
        add(new Link("newButton") {

            @Override
            public void onClick() {
                setResponsePage(new PlayerPage());
            }
        });
    }
}
