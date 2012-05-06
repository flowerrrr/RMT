package de.flower.rmt.ui.page.account;

import de.flower.common.ui.ajax.markup.html.tab.AbstractAjaxTabbedPanel;
import de.flower.rmt.model.User;
import de.flower.rmt.service.security.ISecurityService;
import de.flower.rmt.ui.model.UserModel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class AccountTabPanel extends AbstractAjaxTabbedPanel<User> {

    public static final int PASSWORD_RESET_PANEL_INDEX = 1;

    @SpringBean
    protected ISecurityService securityService;

    public AccountTabPanel() {
    }

    @Override
    protected void addTabs(final List<ITab> tabs) {
        final IModel<User> model = new UserModel(securityService.getUser());
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

    }
}
