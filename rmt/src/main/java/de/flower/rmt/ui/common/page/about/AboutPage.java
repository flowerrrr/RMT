package de.flower.rmt.ui.common.page.about;

import de.flower.rmt.ui.common.page.AbstractBaseLayoutPage;
import de.flower.rmt.ui.common.page.AnonymousNavigationPanel;

/**
 * @author flowerrrr
 */
public class AboutPage extends AbstractBaseLayoutPage {

    public AboutPage() {
        setHeading("about.heading", "about.heading.sub");
        add(new AnonymousNavigationPanel());
        addMainPanel(new AboutPanel());
    }

    @Override
    protected boolean showAlertMessages() {
        // not nice to see alert messages on this page. confuses the user.
        return false;
    }

}
