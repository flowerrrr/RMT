package de.flower.rmt.ui.page.error;

import de.flower.rmt.ui.page.base.AbstractBaseLayoutPage;
import de.flower.rmt.ui.page.base.AnonymousNavigationPanel;

/**
 * @author flowerrrr
 */
public class PageNotFound404Page extends AbstractBaseLayoutPage {

    public PageNotFound404Page() {
        setHeading("error.404.heading", "error.404.heading.sub");
        add(new AnonymousNavigationPanel());
        addMainPanel(new PageNotFound404Panel());
    }

    @Override
    protected boolean showAlertMessages() {
        // not nice to see alert messages on error pages. confuses the user.
        return false;
    }
}
