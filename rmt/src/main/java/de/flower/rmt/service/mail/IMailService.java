package de.flower.rmt.service.mail;

import de.flower.rmt.model.type.Notification;

/**
 * @author flowerrrr
 */
public interface IMailService {

    void sendMail(String receiver, String subject, String body);

    /**
     * Sends a mail to multiple recipients (all on bcc).
     * Uses pre-configured to-address.
     *
     * @param notification
     */
    void sendMassMail(Notification notification);

}
