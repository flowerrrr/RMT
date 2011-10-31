package de.flower.rmt.ui.player;

import de.flower.rmt.ui.app.AbstractBasePage;
import de.flower.rmt.ui.common.panel.LogoutLink;
import org.apache.wicket.markup.html.basic.Label;
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
        add(new Label("user", getUser().getFullname()));
        add(new LogoutLink("logoutLink", this.getClass()));
    }

}
