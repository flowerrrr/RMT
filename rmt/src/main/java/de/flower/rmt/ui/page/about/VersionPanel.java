package de.flower.rmt.ui.page.about;

import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.ui.app.Version;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

/**
 * @author flowerrrr
 */
public class VersionPanel extends BasePanel {

    public VersionPanel() {
        add(new Label("version", Version.VERSION));

        add(new BookmarkablePageLink("changeLogLink", ChangeLogPage.class));
    }
}
