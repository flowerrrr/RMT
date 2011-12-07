package de.flower.rmt.ui.player.page.account;

import de.flower.rmt.model.User;
import de.flower.rmt.test.AbstractWicketTests;
import de.flower.rmt.ui.model.UserModel;

/**
 * @author flowerrrr
 */
public class AccountPasswordPanelTest extends AbstractWicketTests {

    @Override
    public void testRender() {
        User user = testData.getTestUser();
        wicketTester.startComponentInPage(new AccountPasswordPanel(new UserModel(user)));
        wicketTester.dumpComponentWithPage();
    }
}
