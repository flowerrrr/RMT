package de.flower.rmt.ui.page.base;

import de.flower.rmt.ui.app.RMTSession;
import de.flower.rmt.ui.app.View;
import de.flower.rmt.ui.site.PanelProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public abstract class AbstractCommonBasePage extends AbstractBaseLayoutPage implements INavigationPanelAware {

    @SpringBean
    private PanelProvider panelProvider;

    public AbstractCommonBasePage() {
        this(null, null);
    }

    public AbstractCommonBasePage(final IModel<?> model, View view) {
        super(model);
        // sub-pages can force certain view
        if (view != null) RMTSession.get().setView(view);

        // use panelProvider to avoid cyclic dependency
        add(panelProvider.getNavigationPanel(this));
    }
}
