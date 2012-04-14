package de.flower.rmt.service;

import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.test.AbstractIntegrationTests;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */

public class ActivityManagerTest extends AbstractIntegrationTests {

    @Test
    public void testOnEventCreatedOrUpdated() {
        Event event = testData.newEvent();
        activityManager.onCreateOrUpdate(event, true);
    }

    @Test
    public void testOnInvitationMailSent() {
        Event event = testData.newEvent();
        activityManager.onInvitationMailSent(event);
    }

    @Test
    public void testOnInvitationUpdate() {
        Event event = testData.createEvent();
        Invitation old = invitationManager.findAllByEventSortedByName(event).get(0);
        // load copy of invitation
        Invitation updated = invitationManager.findAllByEventSortedByName(event).get(0);
        assertFalse(old == updated);
        assertTrue(old.equals(updated));
        updated.setStatus(RSVPStatus.ACCEPTED);
        activityManager.onInvitationUpdated(updated, old);
    }

}