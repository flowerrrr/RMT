package de.flower.rmt.service;

import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.Invitation_;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.test.AbstractIntegrationTests;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */

public class InvitationManagerTest extends AbstractIntegrationTests {

    @Test
    public void testMarkInvitationSent() {
        Event event = testData.createEventWithoutResponses();
        final List<String> addressList = new ArrayList<String>();
        for (Invitation invitation : invitationManager.findAllByEvent(event, Invitation_.user)) {
            assertFalse(invitation.isInvitationSent());
            addressList.add(invitation.getEmail());
        }
        invitationManager.markInvitationSent(event, addressList);
        for (Invitation invitation : invitationManager.findAllByEvent(event)) {
            assertTrue(invitation.isInvitationSent());
        }
    }
}