package de.flower.rmt.ui.page.base;

import de.flower.rmt.ui.app.View;
import de.flower.rmt.ui.page.base.manager.NavigationPanel;
import org.apache.commons.lang3.EnumUtils;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;

/**
 * @author flowerrrr
 */
public abstract class AbstractCommonBasePage extends AbstractBaseLayoutPage implements INavigationPanelAware, IViewAware {

    // by default view is set to player. will be overridden on demand.
    protected View view = View.PLAYER;

    public AbstractCommonBasePage() {
        this(null);
    }

    public AbstractCommonBasePage(final IModel<?> model) {
        super(model);

        // RMT-693
        // nav bar depends on view-parameter, then user role
        if (getUserDetails().isManager()) {
            String param = RequestCycle.get().getRequest().getRequestParameters().getParameterValue(View.PARAM_VIEW).toString();
            View v = EnumUtils.getEnum(View.class, param);
            if (v != null) {
                view = v;
            } else {
                view = View.MANAGER;
            }
        }
        // navigation panel depends on users role
        if (view == View.MANAGER) {
            add(new NavigationPanel(this));
        } else {
            add(new de.flower.rmt.ui.page.base.player.NavigationPanel(this));
        }
    }

    public View getView() {
        return view;
    }
}
