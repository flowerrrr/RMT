package de.flower.rmt.service.mail;

import de.flower.rmt.test.AbstractIntegrationTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
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

    /**
     * Sends a mail to mailinator.com.
     */
    @Test
    public void testSendMail() {
        String body = "This mail is generated by " + this.getClass().getName();
        body += "\nSome umlaute: äöüÄÖÜß\n";
        securityService.getCurrentUser().setEmail("no-reply@flower.de");
        mailService.sendMail("das-tool-test@mailinator.com", "Unit test generated mail", body);
        log.info("Open http://das-tool-test.mailinator.com to verify mail has been sent.");
    }

}
