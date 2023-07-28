package de.flower.rmt.ui.page.base.player;

import de.flower.rmt.ui.app.View;
import de.flower.rmt.ui.page.base.AbstractCommonBasePage;
import de.flower.rmt.ui.page.base.INavigationPanelAware;
import org.apache.wicket.model.IModel;


public abstract class PlayerBasePage extends AbstractCommonBasePage implements INavigationPanelAware {

    public PlayerBasePage() {
        this(null);
    }

    public PlayerBasePage(final IModel<?> model) {
        super(model, View.PLAYER);
    }

}
