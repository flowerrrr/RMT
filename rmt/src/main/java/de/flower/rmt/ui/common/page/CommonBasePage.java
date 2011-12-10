package de.flower.rmt.ui.common.page;

import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public abstract class CommonBasePage extends AbstractBaseLayoutPage implements INavigationPanelAware {

    public CommonBasePage() {
        this(null);
    }

    public CommonBasePage(final IModel<?> model) {
        super(model);

        // navigation panel depends on users role
        if (getUser().isManager()) {
            add(new de.flower.rmt.ui.manager.NavigationPanel(this));
        } else {
            add(new de.flower.rmt.ui.player.NavigationPanel(this));
        }
    }
}
