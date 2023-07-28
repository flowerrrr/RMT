package de.flower.rmt.ui.page.account;

import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.test.AbstractRMTWicketIntegrationTests;
import de.flower.rmt.ui.model.UserModel;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


public class AccountTabPanelTest extends AbstractRMTWicketIntegrationTests {

    @Test
    public void testRender() {
        final IModel<User> model = new UserModel(securityService.getUser());
        wicketTester.startComponentInPage(new AccountTabPanel(model));
        wicketTester.dumpComponentWithPage();
    }

    @Test
    public void testShowTabPanel() {
        PageParameters params = new PageParameters();
        params.set(AccountTabPanel.TAB_INDEX_KEY, AccountTabPanel.PASSWORD_RESET_PANEL_INDEX);
        wicketTester.startPage(AccountPage.class, params);
        wicketTester.dumpPage();
        Component panel = wicketTester.getComponentFromLastRenderedPage("tabs:panel");
        assertEquals(panel.getClass(), AccountPasswordPanel.class);
    }
}
