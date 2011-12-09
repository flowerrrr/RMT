package de.flower.rmt.ui.manager.page.players;

import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.manager.page.player.PlayerEditPage;
import org.apache.wicket.markup.html.link.Link;

/**
 * @author flowerrrr
 */
public class PlayersSecondaryPanel extends BasePanel {

    public PlayersSecondaryPanel() {
        add(new Link("newButton") {

            @Override
            public void onClick() {
                setResponsePage(new PlayerEditPage());
            }
        });
    }
}
