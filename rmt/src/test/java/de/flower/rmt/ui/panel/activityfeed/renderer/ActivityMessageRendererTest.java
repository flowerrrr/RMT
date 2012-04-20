package de.flower.rmt.ui.panel.activityfeed.renderer;

import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.event.EventType;
import de.flower.rmt.model.type.activity.EmailSentMessage;
import de.flower.rmt.model.type.activity.EventUpdateMessage;
import de.flower.rmt.model.type.activity.InvitationUpdateMessage;
import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import org.testng.annotations.Test;

import java.util.Date;

/**
 * @author flowerrrr
 */
public class ActivityMessageRendererTest extends AbstractRMTWicketMockitoTests {

    @Test
    public void testEventCreated() {
        EventUpdateMessage message = new EventUpdateMessage(testData.newEvent());
        message.setCreated(true);
        message.setManagerName("Uli Hoeneß");
        message.setTeamName("FC Bayern");
        message.setEventId(1L);
        message.setEventDate(new Date());
        message.setEventType(EventType.Match);
        String s = ActivityMessageRenderer.toString(message);
        log.info(message + " rendered to [" + s + "]");
    }

    @Test
    public void testEventUpdated() {
        EventUpdateMessage message = new EventUpdateMessage(testData.newEvent());
        message.setCreated(false);
        message.setManagerName("Uli Hoeneß");
        message.setTeamName("FC Bayern");
        message.setEventDate(new Date());
        message.setEventId(1L);
        for (EventType eventType : EventType.values()) {
            message.setEventType(eventType);
            String s = ActivityMessageRenderer.toString(message);
            log.info(message + " rendered to [" + s + "]");
        }
    }

    @Test
    public void testEmailSent() {
        EmailSentMessage message = new EmailSentMessage(testData.newEvent());
        message.setManagerName("Uli Hoeneß");
        message.setEventDate(new Date());
        message.setEventId(1L);
        for (EventType eventType : EventType.values()) {
            message.setEventType(eventType);
            String s = ActivityMessageRenderer.toString(message);
            log.info(message + " rendered to [" + s + "]");
        }
    }

    @Test
    public void testInvitationStatusUpdated() {
        InvitationUpdateMessage message = new InvitationUpdateMessage(testData.newEvent());
        message.setUserName("Gerd Müller");
        message.setStatus(RSVPStatus.ACCEPTED);
        message.setEventDate(new Date());
        message.setEventId(1L);
        for (EventType eventType : EventType.values()) {
            message.setEventType(eventType);
            String s = ActivityMessageRenderer.toString(message);
            log.info(message + " rendered to [" + s + "]");
        }
    }

    @Test
    public void testInvitationCommentUpdated() {
        InvitationUpdateMessage message = new InvitationUpdateMessage(testData.newEvent());
        message.setUserName("Gerd Müller");
        message.setComment("Gfrei mi narrisch aufs Spiel und auf die 3. Halbzeit hinterher umso mehr.");
        message.setEventId(1L);
        message.setEventDate(new Date());
        for (EventType eventType : EventType.values()) {
            message.setEventType(eventType);
            String s = ActivityMessageRenderer.toString(message);
            log.info(message + " rendered to [" + s + "]");
        }
    }

    @Test
    public void testInvitationStatusAndCommentUpdated() {
        InvitationUpdateMessage message = new InvitationUpdateMessage(testData.newEvent());
        message.setUserName("Gerd Müller");
        message.setStatus(RSVPStatus.ACCEPTED);
        message.setComment("Gfrei mi narrisch aufs Spiel und auf die 3. Halbzeit hinterher umso mehr.");
        message.setEventId(1L);
        message.setEventDate(new Date());
        for (EventType eventType : EventType.values()) {
            message.setEventType(eventType);
            String s = ActivityMessageRenderer.toString(message);
            log.info(message + " rendered to [" + s + "]");
        }
    }

    @Test
    public void testInvitationStatusAndManagerCommentUpdated() {
        InvitationUpdateMessage message = new InvitationUpdateMessage(testData.newEvent());
        message.setUserName("Gerd Müller");
        message.setStatus(RSVPStatus.ACCEPTED);
        message.setComment("Gfrei mi narrisch aufs Spiel und auf die 3. Halbzeit hinterher umso mehr.");
        message.setEventId(1L);
        message.setEventDate(new Date());
        for (EventType eventType : EventType.values()) {
            message.setEventType(eventType);
            String s = ActivityMessageRenderer.toString(message);
            log.info(message + " rendered to [" + s + "]");
        }
    }
}
