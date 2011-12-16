package de.flower.rmt.ui.common.panel;

import de.flower.rmt.ui.common.page.AbstractBasePage;
import de.flower.rmt.ui.common.page.account.AccountMainPanel;
import de.flower.rmt.ui.common.page.account.AccountPage;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author flowerrrr
 */
public class PasswordChangeRequiredMessage extends AlertMessage {

    public PasswordChangeRequiredMessage() {
        super(new ResourceModel("welcome.message.change.password"),
                new ResourceModel("button.change.password"));
    }

    @Override
    public boolean onClick(final AlertMessagePanel alertMessagePanel) {
        // pass name of tabbed panel to display when page is rendered
        final PageParameters params = new PageParameters();
        params.set(AccountMainPanel.TAB_INDEX_KEY, AccountMainPanel.PASSWORD_RESET_PANEL_INDEX);
        alertMessagePanel.setResponsePage(AccountPage.class, params);
        return true; // hide message
    }

    @Override
    public boolean isVisible(final AlertMessagePanel alertMessagePanel) {
        AbstractBasePage page = (AbstractBasePage) alertMessagePanel.getPage();
        return page.getUserDetails().getUser().hasInitialPassword();
    }
}
