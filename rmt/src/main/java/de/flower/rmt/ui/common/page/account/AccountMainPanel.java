package de.flower.rmt.ui.common.page.account;

import de.flower.rmt.model.User;
import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.model.UserModel;
import org.apache.wicket.Page;
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

    public static final String TAB_INDEX_KEY = "TAB_INDEX_KEY";

    public static final int PASSWORD_RESET_PANEL_INDEX = 1;

    private AjaxTabbedPanel tabbedPanel;

    public AccountMainPanel() {
        final IModel<User> model = new UserModel(getUserDetails().getUser());

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
        tabbedPanel = new AjaxTabbedPanel("tabs", tabs);
        add(tabbedPanel);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        selectActiveTab();
    }

    /**
     * Cannot be called in constructor cause page is not available then.
     */
    private void selectActiveTab() {
        Page page = findPage();
        if (page != null) {
            int index = page.getPageParameters().get(TAB_INDEX_KEY).toInt(0);
            tabbedPanel.setSelectedTab(index);
        }
    }
}
