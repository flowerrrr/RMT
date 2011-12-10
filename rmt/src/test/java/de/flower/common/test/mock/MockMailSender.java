package de.flower.common.test.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * @author flowerrrr
 */
public class MockMailSender implements MailSender {

    private final static Logger log = LoggerFactory.getLogger(MockMailSender.class);

    @Override
    public void send(final SimpleMailMessage simpleMessage) throws MailException {
        log.info("Sending mail:\n" + simpleMessage.toString());
    }

    @Override
    public void send(final SimpleMailMessage[] simpleMessages) throws MailException {
        throw new UnsupportedOperationException("Feature not implemented!");
    }
}
