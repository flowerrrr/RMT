package de.flower.rmt.service;

import de.flower.rmt.model.db.entity.Activity;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.Invitation_;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.type.RSVPStatus;
import de.flower.rmt.model.db.type.activity.EventUpdateMessage;
import de.flower.rmt.test.AbstractRMTIntegrationTests;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */

public class ActivityManagerTest extends AbstractRMTIntegrationTests {

    @Test
    public void testOnEventCreatedOrUpdated() {
        Event event = testData.createEvent();
        activityManager.onCreateOrUpdateEvent(event, EventUpdateMessage.Type.CREATED);
    }

    @Test
    public void testOnInvitationMailSent() {
        Event event = testData.createEvent();
        activityManager.onInvitationMailSent(event);
    }

    @Test
    public void testOnInvitationUpdate() {
        Event event = testData.createEvent();
        Invitation origInvitation = invitationManager.findAllByEvent(event, Invitation_.event, Invitation_.user).get(0);
        // load copy of invitation
        Invitation updatedInvitation = invitationManager.findAllByEvent(event).get(0);
        assertFalse(origInvitation == updatedInvitation);
        assertTrue(origInvitation.equals(updatedInvitation));
        updatedInvitation.setStatus(RSVPStatus.ACCEPTED);
        activityManager.onInvitationUpdated(updatedInvitation, origInvitation, "some comment", null);
    }

    @Test
    public void testFind() {
        int size = 10;
        testData.createActivities(size);
        List<Activity> list = activityManager.findLastN(0, size);
        assertEquals(list.size(), size);
    }

}