package de.flower.rmt.ui.page.error;

import de.flower.rmt.ui.page.base.AbstractBaseLayoutPage;
import de.flower.rmt.ui.page.base.AnonymousNavigationPanel;

/**
 * @author flowerrrr
 */
public class AccessDenied403Page extends AbstractBaseLayoutPage {

    public AccessDenied403Page() {
        setHeading("error.403.heading", "error.403.heading.sub");
        add(new AnonymousNavigationPanel());
        addMainPanel(new AccessDenied403Panel());
    }

    @Override
    protected boolean showAlertMessages() {
        // not nice to see alert messages on error pages. confuses the user.
        return false;
    }
}
