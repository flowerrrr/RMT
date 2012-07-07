package de.flower.rmt.service;

import de.flower.rmt.model.db.entity.*;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.type.RSVPStatus;
import de.flower.rmt.model.dto.CalItemDto;
import de.flower.rmt.test.AbstractRMTIntegrationTests;
import de.flower.rmt.util.Dates;
import org.testng.annotations.Test;

import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */

public class InvitationManagerTest extends AbstractRMTIntegrationTests {

    @Test
    public void testInvitationSave() {
        Event event = testData.createEvent();
        Invitation invitation = invitationManager.findAllByEvent(event).get(0);
        invitationManager.save(invitation, "some comment");
        invitation = invitationManager.loadById(invitation.getId(), Invitation_.comments);
        assertEquals(invitation.getComments().size(), 1);
        Comment comment = invitation.getComments().get(0);
        assertEquals(comment.getAuthor(), securityService.getUser());
        assertEquals(comment.getText(), "some comment");

        // update comment
        invitationManager.save(invitation, "new comment");
        invitation = invitationManager.loadById(invitation.getId(), Invitation_.comments);
        assertEquals(invitation.getComments().size(), 1);
        comment = invitation.getComments().get(0);
        assertEquals(comment.getText(), "new comment");

    }

    @Test
    public void testMarkInvitationSent() {
        Event event = testData.createEvent();
        final List<String> addressList = new ArrayList<String>();
        List<Invitation> invitations = invitationManager.findAllByEvent(event, Invitation_.user);
        for (Invitation invitation : invitations) {
            assertFalse(invitation.isInvitationSent());
            addressList.add(invitation.getEmail());
        }
        invitationManager.markInvitationSent(event, addressList, null);
        for (Invitation invitation : invitationManager.findAllByEvent(event)) {
            assertTrue(invitation.isInvitationSent());
            assertNotNull(invitation.getInvitationSentDate());
        }

        // use test also for other notification markers
        invitationManager.markNoResponseReminderSent(invitations);
        for (Invitation invitation : invitationManager.findAllByEvent(event)) {
            assertTrue(invitation.isNoResponseReminderSent());
        }

        // use test also for other notification markers
        invitationManager.markUnsureReminderSent(invitations);
        for (Invitation invitation : invitationManager.findAllByEvent(event)) {
            assertTrue(invitation.isUnsureReminderSent());
        }
    }

    @Test
    public void testGetAllRecipientsForEvent() {
        Event event = testData.createEvent();

        // first lookup. must return all invitees
        List<Invitation> invitations = invitationManager.findAllForNotificationByEventSortedByName(event);
        int numAll = invitations.size();

        // disable notification for some of the players
        List<Player> players = playerManager.findAllByTeam(event.getTeam());
        int[] optOuts = {2, 5};
        for (int i : optOuts) {
            Player optOutPlayer = players.get(i);
            optOutPlayer.setNotification(false);
            playerManager.save(optOutPlayer);
        }
        // now find all recipients again. returned list must be missing two invitations
        invitations = invitationManager.findAllForNotificationByEventSortedByName(event);
        assertEquals(invitations.size(), numAll - optOuts.length);
    }

    @Test
    public void testInternetAddress2String() throws UnsupportedEncodingException {
        InternetAddress ia = new InternetAddress("foo@acme.com", "Mesut Özil");
        log.info(ia.toString());
    }

    @Test
    public void testFindAllForNoResponseReminder() {
        Event event = testData.createEvent();
        invitationManager.findAllForNoResponseReminder(event, 0);
    }

    @Test
    public void testFindAllForUnsureReminder() {
        Event event = testData.createEvent();
        invitationManager.findAllForUnsureReminder(event);
    }

    @Test
    public void testAutoDecline() {
        Team team = testData.createTeamWithPlayers("FCB " + System.currentTimeMillis(), 15);
        Event event = testData.createEvent(team, false);
        // now create calendar holiday event for a user
        CalItemDto dto = new CalItemDto();
        dto.setType(CalItem.Type.HOLIDAY);
        dto.setSummary("South Tirol");
        dto.setStartDateTime(event.getDateTime());
        dto.setEndDateTime(event.getDateTime().plusDays(3));
        dto.setAllDay(true);
        dto.setAutoDecline(true);
        User user = team.getPlayers().get(0).getUser();
        calendarManager.save(dto, user);
        // now create invitation for this user
        invitationManager.addUsers(event, Arrays.asList(user.getId()));
        // find invitation and assert status
        Invitation invitation = invitationManager.findByEventAndUser(event, user);
        assertEquals(invitation.getStatus(), RSVPStatus.DECLINED);
        Comment comment = commentManager.findByInvitationAndAuthor(invitation, user, 0);
        assertEquals(comment.getText(), String.format("Urlaub (%s - %s)", Dates.formatDateShort(dto.getStartDateTime().toDate()), Dates.formatDateShort(dto.getEndDateTime().toDate())));
    }

}