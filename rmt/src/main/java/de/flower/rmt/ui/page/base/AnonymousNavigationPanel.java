package de.flower.rmt.ui.page.base;

import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.ui.app.Links;
import de.flower.rmt.ui.page.about.AboutPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

/**
 * @author flowerrrr
 */
public class AnonymousNavigationPanel extends BasePanel {

    public static final String HOME = "home";

    public AnonymousNavigationPanel() {
        super("navigationPanel");

        add(new BookmarkablePageLink("aboutLink", AboutPage.class));

        add(NavigationPanel.createMenuItem(HOME, Links.contextRoot("home"), null));
    }

}
