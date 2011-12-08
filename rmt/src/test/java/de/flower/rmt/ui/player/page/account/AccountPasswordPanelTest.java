package de.flower.rmt.ui.player.page.account;

import de.flower.rmt.model.User;
import de.flower.rmt.test.AbstractWicketTests;
import de.flower.rmt.ui.model.UserModel;
import org.apache.wicket.util.tester.FormTester;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class AccountPasswordPanelTest extends AbstractWicketTests {

    @Override
    @Test
    public void testRender() {
        User user = testData.getTestUser();
        wicketTester.startComponentInPage(new AccountPasswordPanel("panel", new UserModel(user)));
        wicketTester.dumpComponentWithPage();
    }

    @Test
    public void testValidation() {
        User user = testData.createUsers(1).get(0);
        wicketTester.startComponentInPage(new AccountPasswordPanel("panel", new UserModel(user)));
        wicketTester.dumpComponentWithPage();
        FormTester formTester = wicketTester.newFormTester("form");
        formTester.setValue("oldPassword:input", "uifaöjsdkflöajdsf");
        formTester.setValue("newPassword:input", "jkalsjdkfldsjf");
        formTester.setValue("newPasswordRepeat:input", "jkalsjdkfldsjf");
        wicketTester.clickLink("submitButton");
        wicketTester.dumpComponentWithPage();
        String message = "Ungültiges Passwort";
        wicketTester.assertErrorMessagesContains(new String[] {message});
        wicketTester.assertVisible("form:formFeedbackPanel:feedback:feedbackul:messages");
        wicketTester.assertContains(message);
    }
}
