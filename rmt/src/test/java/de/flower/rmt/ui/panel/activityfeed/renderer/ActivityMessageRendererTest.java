package de.flower.rmt.ui.panel.activityfeed.renderer;

import de.flower.rmt.model.db.entity.event.EventType;
import de.flower.rmt.model.db.type.RSVPStatus;
import de.flower.rmt.model.db.type.activity.BlogUpdateMessage;
import de.flower.rmt.model.db.type.activity.EmailSentMessage;
import de.flower.rmt.model.db.type.activity.EventUpdateMessage;
import de.flower.rmt.model.db.type.activity.EventUpdateMessage.Type;
import de.flower.rmt.model.db.type.activity.InvitationUpdateMessage;
import de.flower.rmt.model.db.type.activity.InvitationUpdateMessage2;
import de.flower.rmt.service.IUrlProvider;
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
    private IUrlProvider urlProvider;

    @BeforeMethod
    public void setUp() {
        when(urlProvider.deepLinkEvent(anyLong())).thenReturn("http://flower.de/das-tool/event/19");
        when(urlProvider.deepLinkBlog(anyLong())).thenReturn("http://flower.de/das-tool/blog/19");
    }

    @Test
    public void testEventCreated() {
        EventUpdateMessage message = new EventUpdateMessage(testData.newEvent());
        message.setType(EventUpdateMessage.Type.CREATED);
        message.setManagerName("Uli Hoeneß");
        message.setTeamName("FC Bayern");
        message.setEventType(EventType.Match);
        String s = ActivityMessageRenderer.toString(message);
        log.info(message + " rendered to [" + s + "]");
    }

    @Test
    public void testEventUpdated() {
        EventUpdateMessage message = new EventUpdateMessage(testData.newEvent());
        message.setType(EventUpdateMessage.Type.UPDATED);
        message.setManagerName("Uli Hoeneß");
        message.setTeamName("FC Bayern");
        for (EventType eventType : EventType.values()) {
            message.setEventType(eventType);
            String s = ActivityMessageRenderer.toString(message);
            log.info(message + " rendered to [" + s + "]");
        }
    }

    @Test
    public void testEventCanceled() {
        EventUpdateMessage message = new EventUpdateMessage(testData.newEvent());
        message.setType(EventUpdateMessage.Type.CANCELED);
        message.setManagerName("Uli Hoeneß");
        message.setTeamName("FC Bayern");
        for (EventType eventType : EventType.values()) {
            message.setEventType(eventType);
            String s = ActivityMessageRenderer.toString(message);
            log.info(message + " rendered to [" + s + "]");
        }
    }

    @Test
    public void testLineupPublished() {
        EventUpdateMessage message = new EventUpdateMessage(testData.newEvent());
        message.setType(Type.LINEUP_PUBLISHED);
        message.setManagerName("Uli Hoeneß");
        String s = ActivityMessageRenderer.toString(message);
        log.info(message + " rendered to [" + s + "]");
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

    // tests for old InvitationUpdateMessage

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
    public void testInvitationCommentUpdatedByManager() {
        InvitationUpdateMessage message = new InvitationUpdateMessage(testData.newEvent());
        message.setUserName("Gerd Müller");
        message.setManagerName("Uli Hoeneß");
        // message.setStatus(RSVPStatus.ACCEPTED);
        message.setManagerComment("Gfrei mi narrisch aufs Spiel und auf die 3. Halbzeit hinterher umso mehr.");
        message.setEventId(1L);
        message.setEventDate(new Date());
        for (EventType eventType : EventType.values()) {
            message.setEventType(eventType);
            String s = ActivityMessageRenderer.toString(message);
            log.info(message + " rendered to [" + s + "]");
        }
    }

    @Test
    public void testInvitationStatusAndCommentUpdatedByManager() {
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

    // tests for new InvitationUpdateMessage2

    @Test
    public void testInvitationStatusUpdated2() {
        InvitationUpdateMessage2 message = new InvitationUpdateMessage2(testData.newEvent());
        message.setInvitationUserName("Gerd Müller");
        message.setAuthorUserName("Gerd Müller");
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
    public void testInvitationCommentUpdated2() {
        InvitationUpdateMessage2 message = new InvitationUpdateMessage2(testData.newEvent());
        message.setInvitationUserName("Gerd Müller");
        message.setAuthorUserName("Gerd Müller");
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
    public void testInvitationStatusAndCommentUpdated2() {
        InvitationUpdateMessage2 message = new InvitationUpdateMessage2(testData.newEvent());
        message.setInvitationUserName("Gerd Müller");
        message.setAuthorUserName("Gerd Müller");
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
    public void testInvitationStatusUpdatedByManager2() {
        InvitationUpdateMessage2 message = new InvitationUpdateMessage2(testData.newEvent());
        message.setInvitationUserName("Gerd Müller");
        message.setAuthorUserName("Uli Hoeneß");
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
    public void testInvitationStatusAndCommentUpdatedByManager2() {
        InvitationUpdateMessage2 message = new InvitationUpdateMessage2(testData.newEvent());
        message.setInvitationUserName("Gerd Müller");
        message.setAuthorUserName("Uli Hoeneß");
        message.setStatus(RSVPStatus.ACCEPTED);
        message.setComment("Gfrei mi narrisch aufs Spiel und auf die 3. Halbzeit hinterher umso mehr.");
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

    @Test
    public void testInvitationCommentUpdatedByOther2() {
        InvitationUpdateMessage2 message = new InvitationUpdateMessage2(testData.newEvent());
        message.setInvitationUserName("Gerd Müller");
        message.setAuthorUserName("Uli Hoeneß");
        // message.setStatus(RSVPStatus.ACCEPTED);
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
    public void testBlogArticleCreate() {
        BlogUpdateMessage message = new BlogUpdateMessage();
        message.setHeading("FC Bayern tries to buy CR7");
        message.setArticleId(2L);
        message.setAuthorUserName("Oliver Kahn");

        String s = ActivityMessageRenderer.toString(message);
        log.info(message + " rendered to [" + s + "]");
    }

    @Test
    public void testBlogCommentCreate() {
        BlogUpdateMessage message = new BlogUpdateMessage();
        message.setHeading("FC Bayern tries to buy CR7");
        message.setArticleId(2L);
        message.setAuthorUserName("Oliver Kahn");
        message.setCommment(true);
        String s = ActivityMessageRenderer.toString(message);
        log.info(message + " rendered to [" + s + "]");
    }
}
