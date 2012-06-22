package de.flower.rmt.ui.page.base.manager;

import de.flower.rmt.ui.app.View;
import de.flower.rmt.ui.page.base.AbstractBaseLayoutPage;
import de.flower.rmt.ui.page.base.INavigationPanelAware;
import de.flower.rmt.ui.page.base.IViewAware;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public abstract class ManagerBasePage extends AbstractBaseLayoutPage implements INavigationPanelAware, IViewAware {

    public ManagerBasePage() {
        this(null);
    }

    public ManagerBasePage(IModel<?> model) {
        super(model);

        add(new NavigationPanel(this));
    }

    @Override
    public View getView() {
        return View.MANAGER;
    }

}
