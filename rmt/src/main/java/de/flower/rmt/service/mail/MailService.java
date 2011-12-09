package de.flower.rmt.service.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author flowerrrr
 */
@Service
public class MailService implements IMailService {

    private final static Logger log = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private SimpleMailMessage templateMessage;

    @Autowired
    private MailSender mailSender;

    @Override
    public void sendMail(final String replyTo, final String receiver, final String subject, final String content) {
        sendMail(null, replyTo, Arrays.asList(new String[]{receiver}), null, subject, content, null);
    }

    /**
     * Send mail.
     *
     * @param sender  the sender
     * @param toList  the to list
     * @param ccList  the cc list
     * @param subject the subject
     * @param content the content
     * @param bccList the bcc list
     * @throws RuntimeException the mail interface exception
     */
    public final void sendMail(String sender, String replyTo, final List<String> toList, final List<String> ccList, final String subject, final String content, List<String> bccList) {
        // Create a thread safe "copy" of the template message and customize it
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        if (sender != null) {
            msg.setFrom(sender);
        }
        if (replyTo != null) {
            msg.setReplyTo(replyTo);
        }
        msg.setTo((String[]) toList.toArray());
        if (ccList != null) {
            msg.setCc((String[]) ccList.toArray());
        }
        if (bccList != null) {
            msg.setBcc((String[]) bccList.toArray());
        }
        msg.setSubject(subject);
        msg.setText(content);
        try{
            this.mailSender.send(msg);
        }
        catch(MailException e) {
            log.error("Error sending mail.", e);
            throw new RuntimeException(e);
        }
    }

}
