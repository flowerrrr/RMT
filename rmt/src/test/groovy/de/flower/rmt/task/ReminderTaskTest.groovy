package de.flower.rmt.task;


import de.flower.rmt.model.db.entity.Invitation_
import de.flower.rmt.model.db.type.RSVPStatus
import de.flower.rmt.test.AbstractRMTWicketIntegrationTests
import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration
import org.testng.annotations.Test
import static org.testng.Assert.assertEquals
import static org.testng.Assert.assertTrue

/**
 * Must extend wicket tests as long as resource string lookup in notification service is done with wicket libs.
 *
 * @author flowerrrr
 */
@ContextConfiguration(classes = [Config.class])
public class ReminderTaskTest extends AbstractRMTWicketIntegrationTests {

    @Configuration
    public static class Config {

        @Bean
        ReminderTask reminderTask() {
            return new ReminderTask();
        }
    }

    @Autowired
    private ReminderTask reminderTask;


    @Test
    public void testSendNoResponseReminder() {
        def hours = 48
        def event = testData.createEvent();
        event.setDateTime(new DateTime().plusHours(hours + 1))
        eventManager.save(event)
        // mark invitation sent
        def addressList = invitationManager.findAllByEvent(event, Invitation_.user).collect { it.getUser().getEmail() }
        assertTrue(addressList.size() > 0)
        invitationManager.markInvitationSent(event, addressList, new DateTime().minusHours(hours + 1).toDate())
        def invitations = invitationManager.findAllForNoResponseReminder(event, hours)
        assertTrue(invitations.size() > 0)

        reminderTask.sendNoResponseReminder();

        // refresh invitations from db
        invitations = invitations.collect { invitationRepo.findOne(it.getId()) }
        invitations.each { assertTrue(it.isNoResponseReminderSent()) }

        assertEquals(invitationManager.findAllForNoResponseReminder(event, hours).size(), 0)
    }

    @Test
    public void testSendUnsureReminder() {
        def hours = 12
        def event = testData.createEvent();
        event.setDateTime(new DateTime().plusHours(12))
        eventManager.save(event)

        // mark some invitations as unsure
        def invitations = invitationManager.findAllByEvent(event)
        invitations.each {
            it.setStatus(RSVPStatus.UNSURE)
            invitationRepo.save(it)
        }

        invitations = invitationManager.findAllForUnsureReminder(event)
        assertTrue(invitations.size() > 0)

        reminderTask.sendUnsureReminder();

        // refresh invitations from db
        invitations = invitations.collect { invitationRepo.findOne(it.getId()) }
        invitations.each { assertTrue(it.isUnsureReminderSent()) }

        assertEquals(invitationManager.findAllForUnsureReminder(event).size(), 0)

    }
}