package de.flower.rmt.service.mail;

/**
 * @author flowerrrr
 */
public interface IMailService {

    void sendMail(String receiver, String subject, String body);

}
