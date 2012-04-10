package de.flower.rmt.ui.common.page;

import de.flower.rmt.ui.app.HomePageResolver;
import de.flower.rmt.ui.common.panel.AbstractNavigationPanel;
import de.flower.rmt.ui.common.panel.BasePanel;

/**
 * @author flowerrrr
 */
public class AnonymousNavigationPanel extends BasePanel {

    public static final String HOME = "home";

    public AnonymousNavigationPanel() {
        super("navigationPanel");
        add(AbstractNavigationPanel.createMenuItem(HOME, HomePageResolver.getHomePage(), null));
    }

}
