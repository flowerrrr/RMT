package de.flower.rmt.service.mail;

import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.dto.Notification;

import java.util.List;

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

    void sendNoResponseReminder(Event event, List<Invitation> invitations);

    void sendUnsureReminder(Event event, List<Invitation> invitations);
}
