package de.flower.rmt.service.mail;

import de.flower.rmt.model.Invitation;
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
     * Send mail to manager that the inviation status has changed.
     * Usually only called when status changes from accepted -> declined.
     *
     * @param invitation
     */
    void sendStatusChangedMessage(Invitation invitation);

    /**
     * Presets notification with template strings.
     * @param event
     * @param eventLink
     * @return
     */
    Notification newEventNotification(Event event, String eventLink);

}
