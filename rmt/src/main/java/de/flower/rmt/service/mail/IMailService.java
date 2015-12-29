package de.flower.rmt.service.mail;

import de.flower.rmt.model.dto.Notification;
import org.springframework.mail.SimpleMailMessage;

/**
 * @author flowerrrr
 */
public interface IMailService {

    void sendMail(SimpleMailMessage message);

    /**
     * Sends a mail to multiple recipients (individual messages to each recipient).
     *
     * @param notification
     */
    void sendMassMail(Notification notification);

}
