package de.flower.rmt.ui.common.page.account;

import de.flower.rmt.test.AbstractWicketIntegrationTests;
import org.apache.wicket.Component;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */
public class AccountMainPanelTest extends AbstractWicketIntegrationTests {

    @Override
    @Test
    public void testRender() {
        wicketTester.startComponentInPage(new AccountMainPanel());
        wicketTester.dumpComponentWithPage();
    }

    @Test
    public void testShowTabPanel() {
        PageParameters params = new PageParameters();
        params.set(AccountMainPanel.TAB_INDEX_KEY, AccountMainPanel.PASSWORD_RESET_PANEL_INDEX);
        wicketTester.startPage(AccountPage.class, params);
        wicketTester.dumpPage();
        Component panel = wicketTester.getComponentFromLastRenderedPage("tabs:panel");
        assertEquals(panel.getClass(), AccountPasswordPanel.class);
    }
}
