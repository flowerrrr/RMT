package de.flower.rmt.ui.page.base.manager;

import de.flower.rmt.ui.page.base.AbstractBaseLayoutPage;
import de.flower.rmt.ui.page.base.INavigationPanelAware;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public abstract class ManagerBasePage extends AbstractBaseLayoutPage implements INavigationPanelAware {

    public ManagerBasePage() {
        this(null);
    }

    public ManagerBasePage(IModel<?> model) {
        super(model);

        add(new NavigationPanel(this));
    }


}
