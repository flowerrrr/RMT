package de.flower.rmt.service.mail;

import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.db.entity.event.*;
import de.flower.rmt.model.db.type.EventType;
import de.flower.rmt.model.db.type.RSVPStatus;
import de.flower.rmt.model.dto.Notification;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.service.IInvitationManager;
import de.flower.rmt.ui.app.Links;
import de.flower.rmt.ui.markup.html.form.renderer.SurfaceRenderer;
import de.flower.rmt.util.Dates;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author flowerrrr
 */
@Service
public class NotificationService implements INotificationService {

    @Autowired
    private IMailService mailService;

    @Autowired
    private ITemplateService templateService;

    @Autowired
    private IEventManager eventManager;

    @Autowired
    private IInvitationManager invitationManager;

    @Override
    public void sendResetPasswordMail(final User user, final User manager) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("user", user);
        String subject = templateService.mergeTemplate(EmailTemplate.PASSWORD_RESET.getSubject(), model);
        String content = templateService.mergeTemplate(EmailTemplate.PASSWORD_RESET.getContent(), model);
        mailService.sendMail(user.getEmail(), manager.getEmail(), subject, content);
    }

    @Override
    public void sendInvitationNewUser(final User user, final User manager) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("user", user);
        model.put("manager", manager);
        model.put("club", user.getClub());
        String subject = templateService.mergeTemplate(EmailTemplate.INVITATION_NEWUSER.getSubject(), model);
        String content = templateService.mergeTemplate(EmailTemplate.INVITATION_NEWUSER.getContent(), model);
        mailService.sendMail(user.getEmail(), manager.getEmail(), subject, content);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public void sendStatusChangedMessage(final Invitation invitationIn) {
        Invitation invitation = invitationManager.loadById(invitationIn.getId());
        Event event = invitation.getEvent();
        User user = invitation.getUser();
        User manager = invitation.getEvent().getCreatedBy();

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("user", user);
        model.put("event", invitation.getEvent());
        model.put("eventDateTime", Dates.formatDateTimeShortWithWeekday(event.getDateTimeAsDate()));
        model.put("eventType", new ResourceModel(event.getEventType().getResourceKey()).getObject());
        model.put("status", new ResourceModel(RSVPStatus.getResourceKey(invitation.getStatus())).getObject());
        String subject = templateService.mergeTemplate(EmailTemplate.INVITATION_STATUSCHANGED.getSubject(), model);
        String content = templateService.mergeTemplate(EmailTemplate.INVITATION_STATUSCHANGED.getContent(), model);
        mailService.sendMail(manager.getEmail(), null, subject, content);
    }

    @Override
    public Notification newEventNotification(final Event eventIn, String eventLink) {
        Event event = eventManager.loadById(eventIn.getId(), Event_.venue, Event_.team, AbstractSoccerEvent_.uniform, Match_.opponent);
        Notification notification = new Notification();
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("event", event);
        model.put("eventDate", Dates.formatDateMediumWithWeekday(event.getDate()));
        model.put("eventTime", Dates.formatTimeShort(event.getTime()));
        model.put("eventDateTime", Dates.formatDateTimeShortWithWeekday(event.getDateTimeAsDate()));
        model.put("eventType", new ResourceModel(event.getEventType().getResourceKey()).getObject());
        model.put("eventTypeMatch", EventType.Match);
        model.put("eventLink", eventLink);
        model.put("isSoccerEvent", EventType.isSoccerEvent(event));
        if (event.getVenue() != null) {
            model.put("directionsLink", Links.getDirectionsUrl(event.getVenue().getLatLng()));
        }
        if (EventType.isSoccerEvent(event)) {
            AbstractSoccerEvent soccerEvent = (AbstractSoccerEvent) event;
            model.put("kickoffTime", Dates.formatTimeShort(soccerEvent.getKickoff()));
            // TODO (flowerrrr - 08.05.12) reference to ui bundle!!!
            model.put("surfaceList", SurfaceRenderer.renderList(soccerEvent.getSurfaceList()));
            if (soccerEvent.getUniform() != null) {
                model.put("uniform", new StringResourceModel("uniform.set", Model.of(soccerEvent.getUniform())).getObject());
            }
        }
        notification.setSubject(templateService.mergeTemplate(EmailTemplate.NOTIFICATION_EVENT.getSubject(), model));
        notification.setBody(templateService.mergeTemplate(EmailTemplate.NOTIFICATION_EVENT.getContent(), model));
        return notification;
    }
}
