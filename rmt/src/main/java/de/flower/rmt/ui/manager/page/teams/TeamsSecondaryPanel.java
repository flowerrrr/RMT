package de.flower.rmt.ui.manager.page.teams;

import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.markup.html.link.Link;

/**
 * @author flowerrrr
 */
public class TeamsSecondaryPanel extends BasePanel {

    public TeamsSecondaryPanel() {
        add(new Link("newButton") {

            @Override
            public void onClick() {
                setResponsePage(new TeamEditPage());
            }
        });
    }
}
