package de.flower.rmt.ui.panel.activityfeed.renderer;

import de.flower.rmt.model.db.type.EventType;
import de.flower.rmt.model.db.type.RSVPStatus;
import de.flower.rmt.model.db.type.activity.EmailSentMessage;
import de.flower.rmt.model.db.type.activity.EventUpdateMessage;
import de.flower.rmt.model.db.type.activity.InvitationUpdateMessage;
import de.flower.rmt.service.ILinkProvider;
import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Date;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

/**
 * @author flowerrrr
 */
public class ActivityMessageRendererTest extends AbstractRMTWicketMockitoTests {

    @SpringBean
    private ILinkProvider linkProvider;

    @BeforeMethod
    public void setUp() {
        when(linkProvider.deepLinkEvent(anyLong())).thenReturn("http://flower.de/das-tool/player/event/19");
    }

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
        message.setEventDate(new Date());
        message.setEventId(1L);
        for (EventType eventType : EventType.values()) {
            message.setEventType(eventType);
            for (RSVPStatus status : RSVPStatus.values()) {
                if (status == RSVPStatus.NORESPONSE) continue;
                message.setStatus(status);
                String s = ActivityMessageRenderer.toString(message);
                log.info(message + " rendered to [" + s + "]");
            }
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
    public void testInvitationStatusUpdatedByManager() {
        InvitationUpdateMessage message = new InvitationUpdateMessage(testData.newEvent());
        message.setUserName("Gerd Müller");
        message.setManagerName("Uli Hoeneß");
        message.setStatus(RSVPStatus.ACCEPTED);
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
        message.setManagerName("Uli Hoeneß");
        message.setStatus(RSVPStatus.ACCEPTED);
        message.setManagerComment("Gfrei mi narrisch aufs Spiel und auf die 3. Halbzeit hinterher umso mehr.");
        message.setEventId(1L);
        message.setEventDate(new Date());
        for (EventType eventType : EventType.values()) {
            message.setEventType(eventType);
            for (RSVPStatus status : RSVPStatus.values()) {
                if (status == RSVPStatus.NORESPONSE) continue;
                message.setStatus(status);
                String s = ActivityMessageRenderer.toString(message);
                log.info(message + " rendered to [" + s + "]");
            }
        }
    }
}
