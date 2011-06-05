package de.flower.rmt.ui.manager;

import de.flower.rmt.ui.manager.page.myteams.MyTeamsPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @author oblume
 */
public class NavigationPanel extends Panel {

    public NavigationPanel(String id) {
        super(id);

        add(new Link("myteams") {

            @Override
            public void onClick() {
                setResponsePage(MyTeamsPage.class);
            }
        });
    }
}
