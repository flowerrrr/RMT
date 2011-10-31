package de.flower.rmt.ui.manager;

import de.flower.rmt.ui.app.AbstractBasePage;
import de.flower.rmt.ui.common.panel.LogoutLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class ManagerBasePage extends AbstractBasePage {

    public ManagerBasePage() {
        this(null);
    }

    public ManagerBasePage(IModel<?> model) {
        super(model);
        add(new Label("user", getUser().getFullname()));
        add(new LogoutLink("logoutLink", this.getClass()));

        add(new NavigationPanel("navPanel"));
    }
}
