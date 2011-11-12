package de.flower.rmt.ui.manager;

import de.flower.rmt.ui.common.page.AbstractBaseLayoutPage;
import de.flower.rmt.ui.common.page.INavigationPanelAware;
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
