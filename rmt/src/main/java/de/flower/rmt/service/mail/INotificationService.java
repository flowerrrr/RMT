package de.flower.rmt.service.mail;

import de.flower.rmt.model.User;

/**
 * @author flowerrrr
 */
public interface INotificationService {

    void sendResetPasswordMail(User user);

    void sendInvitationNewUser(User user, User manager);
}
