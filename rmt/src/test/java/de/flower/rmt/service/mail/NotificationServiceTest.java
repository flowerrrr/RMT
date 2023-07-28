package de.flower.rmt.service.mail;

import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.Invitation_;
import de.flower.rmt.model.db.entity.Uniform;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.entity.event.Match;
import de.flower.rmt.model.db.type.EventType;
import de.flower.rmt.model.db.type.RSVPStatus;
import de.flower.rmt.model.dto.Notification;
import de.flower.rmt.test.AbstractRMTIntegrationTests;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;


public class NotificationServiceTest extends AbstractRMTIntegrationTests {

    @Test
    public void testSendResetPasswordMail() {
        User user = testData.createUser();
        notificationService.sendResetPasswordMail(user, securityService.getUser());
    }

    @Test
    public void testSendInvitationNewUserMail() {
        User user = testData.createUser();
        notificationService.sendInvitationNewUser(user, securityService.getUser());
    }

    @Test
    public void testStatusChangedMessage() {
        Event event = testData.createEvent();
        Invitation invitation = invitationManager.findAllByEvent(event).get(0);
        invitation.setStatus(RSVPStatus.DECLINED);
        invitationManager.save(invitation);
        notificationService.sendStatusChangedMessage(invitation);
    }

    @Test
    public void testNewEventNotification() {
        Event event = testData.createEvent();
        // load from db to get version with uninitialized associations
        event = eventManager.loadById(event.getId());
        Uniform uniform = uniformManager.findAllByTeam(event.getTeam()).get(0); // must be same as in create event
        Notification notification = notificationService.newEventNotification(event);
        log.info(notification.getSubject());
        log.info(notification.getBody());
        log.info(notification.getAttachment().toString());
        assertFalse(notification.getBody().contains("Gegner"));
        assertTrue(notification.getBody().contains("Trikotsatz: Hemd: " + uniform.getShirt()
                + ", Hose: " + uniform.getShorts() + ", Stutzen: " + uniform.getSocks()));

        // no venue selected yet
        testData.setEventType(EventType.Match);
        Match match = (Match) testData.createEvent();
        match.setVenue(null);
        match.getSurfaceList().clear();
        match.setUniform(null);
        eventManager.save(match);
        notification = notificationService.newEventNotification(match);
        log.info(notification.getSubject());
        log.info(notification.getBody());
        log.info(notification.getAttachment().toString());
        assertTrue(notification.getBody().contains("Ort: <noch nicht festgelegt>"));
        assertTrue(notification.getBody().contains("Untergrund: <unbekannt>"));
        assertTrue(notification.getBody().contains("Trikotsatz: <nicht festgelegt>"));
        assertTrue(notification.getBody().contains("Gegner"));
    }

    @Test
    public void testSendNoResponseReminder() {
        Event event = testData.createEvent();
        List<Invitation> invitations = invitationManager.findAllByEvent(event, Invitation_.user);
        notificationService.sendNoResponseReminder(event, invitations);
    }

    @Test
    public void testSendUnsureReminder() {
        Event event = testData.createEvent();
        List<Invitation> invitations = invitationManager.findAllByEvent(event, Invitation_.user);
        notificationService.sendUnsureReminder(event, invitations);
    }

    @Test
    public void testSendEventCanceledMessage() {
        Event event = testData.createEvent();
        List<Invitation> invitations = invitationManager.findAllByEvent(event, Invitation_.user);
        notificationService.sendEventCanceledMessage(event, invitations);
    }
}
