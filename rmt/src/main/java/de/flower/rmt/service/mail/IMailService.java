package de.flower.rmt.service.mail;

import de.flower.rmt.model.dto.Notification;
import org.springframework.mail.SimpleMailMessage;

/**
 * @author flowerrrr
 */
public interface IMailService {

    void sendMail(SimpleMailMessage message);

    void sendMail(String receiver, final String bcc, String subject, String body);

    /**
     * Sends a mail to multiple recipients (all on bcc).
     * Uses pre-configured to-address.
     *
     * @param notification
     */
    void sendMassMail(Notification notification);

}
