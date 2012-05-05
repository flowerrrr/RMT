package de.flower.rmt.ui.page.about;

import de.flower.rmt.ui.app.RMTApplication;
import de.flower.rmt.ui.panel.BasePanel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

/**
 * @author flowerrrr
 */
public class VersionPanel extends BasePanel {

    public VersionPanel() {
        add(new Label("version", RMTApplication.VERSION));

        add(new Link<Void>("changeLogLink") {
            @Override
            public void onClick() {
                setResponsePage(ChangeLogPage.class);
            }
        });
    }
}
