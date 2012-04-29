package de.flower.rmt.service.mail;

import de.flower.rmt.model.User;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.type.Notification;

/**
 * @author flowerrrr
 */
public interface INotificationService {

    void sendResetPasswordMail(User user, final User manager);

    void sendInvitationNewUser(User user, User manager);

    /**
     * Presets notification with template strings.
     * @param event
     * @param eventLink
     * @return
     */
    Notification newEventNotification(Event event, String eventLink);
}
