package de.flower.rmt.ui.player.page.account;

import de.flower.rmt.model.User;
import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.model.UserModel;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class AccountMainPanel extends BasePanel {

    public AccountMainPanel() {
        IModel<User> model = new UserModel(getUser());
        add(new AccountGeneralPanel(model));
        add(new AccountPasswordPanel(model));
    }

}
