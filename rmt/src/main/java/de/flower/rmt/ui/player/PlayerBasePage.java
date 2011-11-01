package de.flower.rmt.ui.player;

import de.flower.rmt.ui.app.AbstractBasePage;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class PlayerBasePage extends AbstractBasePage {

    public PlayerBasePage() {
        this(null);
    }

    public PlayerBasePage(final IModel<?> model) {
        super(model);

        add(new NavigationPanel("topBar"));
    }

}
