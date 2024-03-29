package de.flower.rmt.service.mail;

import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.dto.Notification;
import de.flower.rmt.test.AbstractRMTIntegrationTests;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.wicket.util.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Start smtp4dev before launching these tests.
 */
// use real mailsender (by defaul all unit-tests use mock-mailsender)
@ContextConfiguration(classes = {MailServiceTest.Config.class})
public class MailServiceTest extends AbstractRMTIntegrationTests {

    @Configuration
    @ImportResource({"/de/flower/rmt/service/mail/mailServiceTest.xml"})
    public static class Config {

    }

    /**
     * Autowiring will ensure that real mailSender is used.
     */
    @Autowired
    private JavaMailSenderImpl mailSender;

    @BeforeMethod
    public void setUp() {
        log.info("Tests require local smtp server.");
        securityService.getUser().setEmail("no-reply@mailinator.com");
    }

    /**
     * Test läuft nicht auf Jenkins
     */
    @Test(enabled = false)
    public void testMassMail() throws IOException {
        Notification notification = new Notification();
        notification.setCopyToMySelf(true);
        notification.setSubject("Unit test generated mail.");

        String body = "This mail is generated by " + this.getClass().getName();
        body += "\nSome umlaute: äöüÄÖÜß\n";
        notification.setBody(body);

        for (int i = 0; i < 4; i++) {
            String prefix = "das-tool-test-" + i;
            notification.addRecipient(prefix + "@mailinator.com", RandomStringUtils.random(20));
        }
        Notification.Attachment attachment = new Notification.Attachment();
        attachment.name = "attachment";
        attachment.contentType = ICalendarHelper.CONTENT_TYPE_MAIL;
        InputStream is = this.getClass().getResourceAsStream("MailServiceTest.ics.vm");
        String text = IOUtils.toString(is);
        attachment.data = ICalendarHelper.getBytes(text);
        notification.setAttachment(attachment);
        mailService.sendMassMail(notification);
    }

    /**
     * Test uses real smtp server.
     * Set to enabled=false before committing.
     */
    @Test(enabled = false)
    public void testICalendarAttachment() {
        // mailSender.setHost("mail.flower.de");
        Event event = testData.createEvent();
        Notification notification = notificationService.newEventNotification(event);
        String address = "oliver.blume.1968@gmail.com";
        notification.addRecipient(address, null);
        mailService.sendMassMail(notification);
        log.info("Check postbox at [{}] for email.", address);
    }

    /**
     * Test uses real smtp server.
     * Set to enabled=false before committing.
     */
    @Test(enabled = false)
    public void testSpam() {
        // mailSender.setHost("92.51.163.51");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("das.tool.flower.de@gmail.com");
        message.setReplyTo("oliver@flower.de");
        message.setTo("oliver.blume.1968@gmail.com");
        message.setSubject("Rückmeldetool: Nur ein kleiner Unit test");
        message.setText("Test test, was man so kennt. Grüße, Wiedersehen.");
        mailService.sendMail(message);
        log.info("Check postbox at [{}] for email.");
    }
}
