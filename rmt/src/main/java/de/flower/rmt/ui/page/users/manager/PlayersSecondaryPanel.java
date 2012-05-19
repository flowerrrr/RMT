package de.flower.rmt.ui.page.users.manager;

import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.ui.markup.html.panel.SearchFilterPanel;
import de.flower.rmt.ui.page.user.manager.PlayerPage;
import org.apache.wicket.markup.html.link.Link;

/**
 * @author flowerrrr
 */
public class PlayersSecondaryPanel extends BasePanel {

    public PlayersSecondaryPanel() {
        setRenderBodyOnly(true);
        add(new Link("newButton") {

            @Override
            public void onClick() {
                setResponsePage(new PlayerPage());
            }
        });

        add(new SearchFilterPanel());
    }
}
