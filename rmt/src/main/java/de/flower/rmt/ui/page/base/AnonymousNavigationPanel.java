package de.flower.rmt.ui.page.base;

import de.flower.rmt.ui.app.HomePageResolver;
import de.flower.rmt.ui.app.Links;
import de.flower.rmt.ui.panel.AbstractNavigationPanel;
import de.flower.rmt.ui.panel.BasePanel;

/**
 * @author flowerrrr
 */
public class AnonymousNavigationPanel extends BasePanel {

    public static final String HOME = "home";

    public AnonymousNavigationPanel() {
        super("navigationPanel");

        add(Links.aboutLink("about"));

        add(AbstractNavigationPanel.createMenuItem(HOME, HomePageResolver.getHomePage(), null));
    }

}
