package de.flower.rmt.ui.page.error;

import de.flower.rmt.ui.page.base.AbstractBaseLayoutPage;
import de.flower.rmt.ui.page.base.AnonymousNavigationPanel;

/**
 * @author flowerrrr
 */
public class PageExpiredPage extends AbstractBaseLayoutPage {

    public PageExpiredPage() {
        setHeading("error.page.expired.heading", "error.page.expired.heading.sub");
        add(new AnonymousNavigationPanel());
        addMainPanel(new PageExpiredPanel());
    }

    @Override
    protected boolean showAlertMessages() {
        // not nice to see alert messages on error pages. confuses the user.
        return false;
    }
}
