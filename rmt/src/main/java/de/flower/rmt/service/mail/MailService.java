package de.flower.rmt.service.mail;

import com.google.common.collect.Sets;
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
import java.util.Set;

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

    @Override
    public final void sendMail(SimpleMailMessage message) {
        // Create a thread safe "copy" of the template message and customize it
        MimeMailMessage msg = newMimeMailMessage();
        message.copyTo(msg);
        sendMail(msg);
    }

    @Override
    public void sendMassMail(final Notification notification) {
        // fields like sender, reply-to, to are preset by default mail template.
        Set<String> recipients = Sets.newHashSet();
        for (InternetAddress iAddress : notification.getRecipients()) {
            recipients.add(iAddress.toString());
        }
        if (notification.isCopyToMySelf()) {
            recipients.add(getCurrentUserEmail());
        }
        for (String recipient : recipients) {
            MimeMailMessage message = newMimeMailMessage();
            message.setTo(recipient);
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
        return message;
    }

    /**
     * Send mail.
     *
     * @throws RuntimeException the mail interface exception
     */
    private void sendMail(MimeMailMessage message) {

        try {
            log.info("Sending mail:\n" + MimeMessageUtils.toString(message.getMimeMessage()));
            mailSender.send(message.getMimeMessage());
        } catch (MailException e) {
            log.error("Error sending mail.", e);
            throw new RuntimeException(e);
        }
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
