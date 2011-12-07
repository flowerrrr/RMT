package de.flower.rmt.ui.player.page.account;

import de.flower.rmt.model.User;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class AccountGeneralPanel extends BasePanel {

    public AccountGeneralPanel(String id, final IModel<User> model) {
        super(id, model);
    }
}
