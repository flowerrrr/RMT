package de.flower.common.test.mock;

import de.flower.rmt.service.mail.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;

/**
 * Does not send email, rather logs it away on console.
 * @author flowerrrr
 */
public class MockJavaMailSender extends JavaMailSenderImpl {

    private final static Logger log = LoggerFactory.getLogger(MockJavaMailSender.class);

    @Override
    public void send(final SimpleMailMessage simpleMessage) throws MailException {
        log.info("NOT Sending mail:\n" + simpleMessage.toString());
        // super.send(simpleMessage);
    }

    @Override
    public void send(final SimpleMailMessage[] simpleMessages) throws MailException {
        throw new UnsupportedOperationException("Feature not implemented!");
    }

    @Override
    public void send(final MimeMessage mimeMessage) throws MailException {
         log.info("NOT Sending mail:\n" + MailService.toString(mimeMessage));
    }

    @Override
    public void send(final MimeMessage[] mimeMessages) throws MailException {
        throw new UnsupportedOperationException("Feature not implemented!");
    }

    @Override
    public void send(final MimeMessagePreparator mimeMessagePreparator) throws MailException {
        throw new UnsupportedOperationException("Feature not implemented!");
    }

    @Override
    public void send(final MimeMessagePreparator[] mimeMessagePreparators) throws MailException {
        throw new UnsupportedOperationException("Feature not implemented!");
    }
}
