package de.flower.rmt.ui.page.base.player;

import de.flower.rmt.ui.page.base.AbstractBaseLayoutPage;
import de.flower.rmt.ui.page.base.INavigationPanelAware;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public abstract class PlayerBasePage extends AbstractBaseLayoutPage implements INavigationPanelAware {

    public PlayerBasePage() {
        this(null);
    }

    public PlayerBasePage(final IModel<?> model) {
        super(model);

        add(new NavigationPanel(this));
    }

}
