package de.flower.rmt.service.mail;

import de.flower.rmt.model.User;
import de.flower.rmt.test.AbstractIntegrationTests;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class NotificationServiceTest extends AbstractIntegrationTests {

    @Test
    public void testSendResetPasswordMail() {
        User user = testData.createUser();
        notificationService.sendResetPasswordMail(user);
    }

    @Test
    public void testSendInvitationNewUserMail() {
        User user = testData.createUser();
        notificationService.sendInvitationNewUser(user, securityService.getCurrentUser());
    }
}
