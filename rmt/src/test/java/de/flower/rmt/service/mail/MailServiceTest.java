package de.flower.rmt.service.mail;

import de.flower.rmt.model.type.Notification;
import de.flower.rmt.test.AbstractIntegrationTests;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
// use real mailsender (by defaul all unit-tests use mock-mailsender)
@ContextConfiguration(locations = { "mailServiceTest.xml" } )
public class MailServiceTest extends AbstractIntegrationTests {

    /**
     * Autowiring will ensure that real mailSender is used.
     */
    @Autowired
    private JavaMailSenderImpl mailSender;

    @Value("${mail.default.undisclosed-recipients}")
    private String undisclosedRecipient;

    String body = "This mail is generated by " + this.getClass().getName();

    @BeforeMethod
    public void setUp() {
        securityService.getUser().setEmail("no-reply@mailinator.com");
        mailSender.setHost("mail.flower.de");
    }

    /**
     * Sends a mail to mailinator.com.
     */
    @Test
    public void testSendMail() {
        body += "\nSome umlaute: äöüÄÖÜß\n";
        mailService.sendMail("das-tool-test@mailinator.com", "Unit test generated mail", body);
        log.info("Open http://das-tool-test.mailinator.com to verify mail has been sent.");
    }

    @Test
    public void testMassMail() {
        Notification notification = new Notification();
        notification.setBccMySelf(true);
        notification.setSubject("Unit test generated mail.");
        notification.setBody(body);
        for (int i = 0; i < 20; i++) {
            String prefix = "das-tool-test-" + i;
            notification.addRecipient(prefix + "@mailinator.com", RandomStringUtils.random(20));
        }
        mailService.sendMassMail(notification);
        // note, that mailinator does not accept bcc messages. so the only mail that should be
        // received is the one of the undisclosed recipient
        log.info("Check " + undisclosedRecipient + " to verify mail has been sent.");
    }

}
