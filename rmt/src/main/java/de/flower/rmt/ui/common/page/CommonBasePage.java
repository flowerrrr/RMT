package de.flower.rmt.ui.common.page;

import de.flower.rmt.service.IRoleManager;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public abstract class CommonBasePage extends AbstractBaseLayoutPage implements INavigationPanelAware {

    @SpringBean
    private IRoleManager roleManager;

    public CommonBasePage() {
        this(null);
    }

    public CommonBasePage(final IModel<?> model) {
        super(model);

        // navigation panel depends on users role
        if (getUserDetails().isManager()) {
            add(new de.flower.rmt.ui.manager.NavigationPanel(this));
        } else {
            add(new de.flower.rmt.ui.player.NavigationPanel(this));
        }
    }
}
