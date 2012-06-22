package de.flower.rmt.ui.page.users;

import de.flower.rmt.ui.markup.html.panel.SearchFilterPanel;
import de.flower.rmt.ui.page.user.manager.PlayerPage;
import de.flower.rmt.ui.panel.RMTBasePanel;
import org.apache.wicket.markup.html.link.Link;

/**
 * @author flowerrrr
 */
public class UsersSecondaryPanel extends RMTBasePanel {

    public UsersSecondaryPanel() {
        setRenderBodyOnly(true);
        add(new Link("newButton") {

            @Override
            public void onClick() {
                setResponsePage(new PlayerPage());
            }

            @Override
            public boolean isVisible() {
                return isManagerView();
            }
        });

        add(new SearchFilterPanel());
    }
}
