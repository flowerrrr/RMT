package de.flower.rmt.ui.player.page.account;

import de.flower.rmt.model.User;
import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.model.UserModel;
import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author flowerrrr
 */
public class AccountMainPanel extends BasePanel {

    public AccountMainPanel() {
        final IModel<User> model = new UserModel(getUser());

        // create a list of ITab objects used to feed the tabbed panel
        List<ITab> tabs = new ArrayList<ITab>();
        tabs.add(new AbstractTab(new ResourceModel("player.account.general")) {
            @Override
            public Panel getPanel(String panelId) {
                return new AccountGeneralPanel(panelId, model);
            }
        });
        tabs.add(new AbstractTab(new ResourceModel("player.account.password")) {
            @Override
            public Panel getPanel(String panelId) {
                return new AccountPasswordPanel(panelId, model);
            }
        });
        add(new AjaxTabbedPanel("tabs", tabs));
    }
}
