package de.flower.rmt.ui.manager.page.player;

import de.flower.rmt.model.User;
import de.flower.rmt.test.AbstractWicketTests;
import de.flower.rmt.ui.model.UserModel;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class ResetPasswordPanelTest extends AbstractWicketTests {

    @Test
    public void testRender() {
        User user = testData.createUser();
        wicketTester.startComponentInPage(new ResetPasswordPanel(new UserModel(user)));
        wicketTester.dumpComponentWithPage();
        wicketTester.assertInvisible("feedbackContainer");
        wicketTester.clickLink("resetButton");
        wicketTester.dumpComponentWithPage();
        wicketTester.assertVisible("feedbackContainer");
        wicketTester.assertContains(user.getEmail());

    }
}
