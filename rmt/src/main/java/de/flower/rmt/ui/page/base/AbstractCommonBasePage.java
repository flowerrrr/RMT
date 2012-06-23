package de.flower.rmt.ui.page.base;

import de.flower.rmt.ui.app.RMTSession;
import de.flower.rmt.ui.app.View;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public abstract class AbstractCommonBasePage extends AbstractBaseLayoutPage implements INavigationPanelAware {

    public AbstractCommonBasePage() {
        this(null, null);
    }

    public AbstractCommonBasePage(final IModel<?> model, View view) {
        super(model);
        // subpages can force certain view
        if (view != null) RMTSession.get().setView(view);

        add(new NavigationPanel(this));
    }
}
