package de.flower.rmt.ui.common.page;

import de.flower.rmt.model.User;
import de.flower.rmt.test.AbstractWicketTests;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class WelcomeMessagesPanelTest extends AbstractWicketTests {

    @Override
    @Test
    public void testRender() {
        wicketTester.startComponentInPage(new WelcomeMessagesPanel());
        wicketTester.dumpComponentWithPage();
    }

    @Test
    public void testPasswordChangeMessage() {
        // first with user that has no initialPassword
        User user = securityService.getCurrentUser();
        user.setInitialPassword(null);
        wicketTester.startComponentInPage(new WelcomeMessagesPanel());
        wicketTester.dumpComponentWithPage();
        wicketTester.assertInvisible("passwordChangeMessage");

        // now set initialpassword -> message must render
        user.setInitialPassword("jlkaöjkf");
        wicketTester.startComponentInPage(new WelcomeMessagesPanel());
        wicketTester.dumpComponentWithPage();
        wicketTester.assertVisible("passwordChangeMessage");

        // click link to acount page -> message should be dismissed and not render anymore
        wicketTester.clickLink("gotoAccount");
        wicketTester.startComponentInPage(new WelcomeMessagesPanel());
        wicketTester.dumpComponentWithPage();
        wicketTester.assertInvisible("passwordChangeMessage");
    }
}
