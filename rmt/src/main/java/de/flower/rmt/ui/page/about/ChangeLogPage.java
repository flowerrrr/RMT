package de.flower.rmt.ui.page.about;

import de.flower.rmt.ui.page.base.AbstractBaseLayoutPage;
import de.flower.rmt.ui.page.base.AnonymousNavigationPanel;

/**
 * @author flowerrrr
 */
public class ChangeLogPage extends AbstractBaseLayoutPage {

    public ChangeLogPage() {
        setHeading("changelog.heading", null);
        add(new AnonymousNavigationPanel());
        addMainPanel(new ChangeLogPanel());
    }

    @Override
    protected boolean showAlertMessages() {
        // not nice to see alert messages on this page. confuses the user.
        return false;
    }

}
