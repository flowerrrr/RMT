package de.flower.rmt.ui.page.base;

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

        add(new NavigationPanel(this));
    }
}
