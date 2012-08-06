package de.flower.rmt.ui.page.account;

import de.flower.common.ui.ajax.markup.html.tab.AbstractAjaxTabbedPanel;
import de.flower.rmt.model.db.entity.User;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import java.util.List;

/**
 * @author flowerrrr
 */
public class AccountTabPanel extends AbstractAjaxTabbedPanel<User> {

    public static final int PASSWORD_RESET_PANEL_INDEX = 1;

    public AccountTabPanel(final IModel<User> model) {
        super(model);
    }

    @Override
    protected void addTabs(final List<ITab> tabs) {
        tabs.add(new AbstractTab(new ResourceModel("player.account.general")) {
            @Override
            public Panel getPanel(String panelId) {
                return new AccountGeneralPanel(panelId, getModel());
            }
        });
        tabs.add(new AbstractTab(new ResourceModel("player.account.password")) {
            @Override
            public Panel getPanel(String panelId) {
                return new AccountPasswordPanel(panelId, getModel());
            }
        });

    }
}
