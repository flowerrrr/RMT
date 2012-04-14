package de.flower.rmt.ui.page.base;

import de.flower.rmt.ui.page.base.manager.NavigationPanel;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public abstract class AbstractCommonBasePage extends AbstractBaseLayoutPage implements INavigationPanelAware {

    public AbstractCommonBasePage() {
        this(null);
    }

    public AbstractCommonBasePage(final IModel<?> model) {
        super(model);

        // navigation panel depends on users role
        if (getUserDetails().isManager()) {
            add(new NavigationPanel(this));
        } else {
            add(new de.flower.rmt.ui.page.base.player.NavigationPanel(this));
        }
    }
}
