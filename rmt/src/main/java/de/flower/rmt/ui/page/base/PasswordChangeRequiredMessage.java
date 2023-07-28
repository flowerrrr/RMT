package de.flower.rmt.ui.page.base;

import de.flower.common.ui.alert.AlertMessage;
import de.flower.common.ui.alert.AlertMessagePanel;
import de.flower.rmt.ui.page.account.AccountPage;
import de.flower.rmt.ui.page.account.AccountTabPanel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;


public class PasswordChangeRequiredMessage extends AlertMessage {

    public PasswordChangeRequiredMessage() {
        super(new ResourceModel("alert.message.change.password"),
                new ResourceModel("button.change.password"));
    }

    @Override
    public boolean onClick(final AlertMessagePanel alertMessagePanel) {
        // pass name of tabbed panel to display when page is rendered
        final PageParameters params = new PageParameters();
        params.set(AccountTabPanel.TAB_INDEX_KEY, AccountTabPanel.PASSWORD_RESET_PANEL_INDEX);
        alertMessagePanel.setResponsePage(AccountPage.class, params);
        return true; // hide message
    }

}
