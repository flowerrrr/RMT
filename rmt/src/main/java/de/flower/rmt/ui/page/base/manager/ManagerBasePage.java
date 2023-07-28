package de.flower.rmt.ui.page.base.manager;

import de.flower.rmt.ui.app.View;
import de.flower.rmt.ui.page.base.AbstractCommonBasePage;
import de.flower.rmt.ui.page.base.INavigationPanelAware;
import org.apache.wicket.model.IModel;


public abstract class ManagerBasePage extends AbstractCommonBasePage implements INavigationPanelAware {

    public ManagerBasePage() {
        this(null);
    }

    public ManagerBasePage(IModel<?> model) {
        super(model, View.MANAGER);
    }
}
