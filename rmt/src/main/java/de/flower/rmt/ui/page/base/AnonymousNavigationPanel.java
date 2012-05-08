package de.flower.rmt.ui.page.base;

import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.ui.app.Links;

/**
 * @author flowerrrr
 */
public class AnonymousNavigationPanel extends BasePanel {

    public static final String HOME = "home";

    public AnonymousNavigationPanel() {
        super("navigationPanel");

        add(Links.aboutLink("about"));

        add(AbstractNavigationPanel.createMenuItem(HOME, Links.homePage("home"), null));
    }

}
