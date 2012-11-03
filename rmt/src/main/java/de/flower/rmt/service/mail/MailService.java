package de.flower.rmt.service.mail;

import de.flower.common.mail.MimeMessageUtils;
import de.flower.common.util.Check;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.dto.Notification;
import de.flower.rmt.security.ISecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
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
    private JavaMailSender mailSender;

    @Autowired
    private ISecurityService securityService;

    @PostConstruct
    public void init() {
        if (mailSender instanceof JavaMailSenderImpl) {
            String host = ((JavaMailSenderImpl) mailSender).getHost();
            log.info("*************************************************************");
            log.info("Smtp Mail Host: " + host);
            log.info("*************************************************************");
        }
    }

    private MimeMailMessage newMimeMailMessage() {
        MimeMailMessage message;
        try {
            message = new MimeMailMessage(new MimeMessageHelper(mailSender.createMimeMessage(), true));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        // preset with defaults from templateMessage
        templateMessage.copyTo(message);
        // use default undisclosed recipients address if no recipient is defined (like in mass mails)
        // check if template is configured correctly.
        Address[] tmp;
        try {
            tmp = message.getMimeMessageHelper().getMimeMessage().getRecipients(Message.RecipientType.TO);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        Check.isTrue(tmp.length > 0);
        return message;
    }

    @Override
    public void sendMassMail(final Notification notification) {
        // fields like sender, reply-to, to are preset by default mail template.
        MimeMailMessage message = newMimeMailMessage();
        List<String> recipients = new ArrayList<String>();
        for (InternetAddress iAddress : notification.getRecipients()) {
            recipients.add(iAddress.toString());
        }
        if (notification.isBccMySelf()) {
            recipients.add(getCurrentUserEmail());
        }
        message.setBcc(recipients.toArray(new String[]{}));
        message.setReplyTo(getCurrentUserEmail());
        message.setSubject(notification.getSubject());
        message.setText(notification.getBody());
        Notification.Attachment attachment = notification.getAttachment();
        if (attachment != null) {
            try {
                message.getMimeMessageHelper().addAttachment(attachment.name, attachment.getInputStreamSource(), attachment.contentType);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
        sendMail(message);
    }

    @Override
    public void sendMail(final String receiver, final String bcc, final String subject, final String content) {
        // mail to single person gets managers email as reply to.
        MimeMailMessage message = newMimeMailMessage();
        message.setReplyTo(getCurrentUserEmail());
        message.setTo(receiver);
        if (bcc != null) {
            message.setBcc(bcc);
        }
        message.setSubject(subject);
        message.setText(content);
        sendMail(message);
    }

    /**
     * Send mail.
     *
     * @throws RuntimeException the mail interface exception
     */
    private final void sendMail(MimeMailMessage message) {

        try {
            log.info("Sending mail:\n" + MimeMessageUtils.toString(message.getMimeMessage()));
            mailSender.send(message.getMimeMessage());
        } catch (MailException e) {
            log.error("Error sending mail.", e);
            throw new RuntimeException(e);
        }
    }

    public final void sendMail(SimpleMailMessage message) {
        // Create a thread safe "copy" of the template message and customize it
        MimeMailMessage msg = newMimeMailMessage();
        message.copyTo(msg);
        sendMail(msg);
    }

    /**
     * Preset reply-to address with email of user that is triggering the email.
     *
     * @return
     */
    private String getCurrentUserEmail() {
        User user = securityService.getUser();
        if (user != null) {
            return user.getEmail();
        } else {
            return null;
        }
    }
}
