package de.flower.rmt.service.mail;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.db.entity.event.*;
import de.flower.rmt.model.db.type.EventType;
import de.flower.rmt.model.db.type.RSVPStatus;
import de.flower.rmt.model.dto.Notification;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.service.IInvitationManager;
import de.flower.rmt.service.ILinkProvider;
import de.flower.rmt.ui.app.Links;
import de.flower.rmt.ui.markup.html.form.renderer.SurfaceRenderer;
import de.flower.rmt.util.Dates;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author flowerrrr
 */
@Service
public class NotificationService implements INotificationService {

    private final static Logger log = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private IMailService mailService;

    @Autowired
    private ITemplateService templateService;

    @Autowired
    private IEventManager eventManager;

    @Autowired
    private IInvitationManager invitationManager;

    @Autowired
    private ILinkProvider linkProvider;

    @Override
    public void sendResetPasswordMail(final User user, final User manager) {
        mailService.sendMail(getResetPasswordMessage(user, manager));
    }

    @VisibleForTesting
    protected SimpleMailMessage getResetPasswordMessage(final User user, final User manager) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmails());
        message.setBcc(manager.getEmail());

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("user", user);
        String subject = templateService.mergeTemplate(EmailTemplate.PASSWORD_RESET.getSubject(), model);
        String content = templateService.mergeTemplate(EmailTemplate.PASSWORD_RESET.getContent(), model);
        message.setSubject(subject);
        message.setText(content);
        return message;
    }

    @Override
    public void sendInvitationNewUser(final User user, final User manager) {
        mailService.sendMail(getInvitationNewUserMessage(user, manager));
    }

    @VisibleForTesting
    protected SimpleMailMessage getInvitationNewUserMessage(User user, User manager) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmails());
        message.setBcc(manager.getEmail());

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("user", user);
        model.put("manager", manager);
        model.put("club", user.getClub());
        String subject = templateService.mergeTemplate(EmailTemplate.INVITATION_NEWUSER.getSubject(), model);
        String content = templateService.mergeTemplate(EmailTemplate.INVITATION_NEWUSER.getContent(), model);

        message.setSubject(subject);
        message.setText(content);
        return message;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public void sendStatusChangedMessage(final Invitation invitationIn) {
        Invitation invitation = invitationManager.loadById(invitationIn.getId());
        SimpleMailMessage message = getStatusChangedMessage(invitation);
        mailService.sendMail(message);
    }

    @VisibleForTesting
    protected SimpleMailMessage getStatusChangedMessage(final Invitation invitation) {
        SimpleMailMessage message = new SimpleMailMessage();

        Event event = invitation.getEvent();
        User user = invitation.getUser();
        User manager = invitation.getEvent().getCreatedBy();
        message.setTo(manager.getEmail());

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("user", user);
        model.put("event", invitation.getEvent());
        model.put("eventDateTime", Dates.formatDateTimeShortWithWeekday(event.getDateTimeAsDate()));
        model.put("eventType", new ResourceModel(event.getEventType().getResourceKey()).getObject());
        model.put("status", new ResourceModel(RSVPStatus.getResourceKey(invitation.getStatus())).getObject());
        String subject = templateService.mergeTemplate(EmailTemplate.INVITATION_STATUSCHANGED.getSubject(), model);
        String content = templateService.mergeTemplate(EmailTemplate.INVITATION_STATUSCHANGED.getContent(), model);

        message.setSubject(subject);
        message.setText(content);
        return message;
    }

    @Override
    public Notification newEventNotification(final Event eventIn, String eventLink) {
        Event event = eventManager.loadById(eventIn.getId(), Event_.venue, Event_.team, AbstractSoccerEvent_.uniform, Match_.opponent);
        return getNewEventNotification(event, eventLink);
    }

    @VisibleForTesting
    protected Notification getNewEventNotification(Event event, String eventLink) {

        Notification notification = new Notification();

        final Map<String, Object> model = getEventDetailsModel(event, eventLink);
        String eventDetails = templateService.mergeTemplate(EmailTemplate.EVENT_DETAILS.getTemplate(), model);
        model.put("eventDetails", eventDetails);

        notification.setSubject(templateService.mergeTemplate(EmailTemplate.NOTIFICATION_EVENT.getSubject(), model));
        notification.setBody(templateService.mergeTemplate(EmailTemplate.NOTIFICATION_EVENT.getContent(), model));
        return notification;
    }

    @Override
    public void sendNoResponseReminder(Event event, final List<Invitation> invitations) {
        log.info("Sending no-response reminder to [{}]", invitations);
        String eventLink = linkProvider.deepLinkEvent(event.getId());
        SimpleMailMessage message = getNoResponseReminderMessage(event, eventLink);

        List<String> to = Lists.newArrayList();
        for (Invitation invitation : invitations) {
            if (invitation.hasEmail()) {
                to.addAll(Arrays.asList(invitation.getEmails()));
            }
        }
        message.setBcc(to.toArray(new String[]{}));

        log.warn("Reminder-Mails are currently mocked.\nSending mail:\n" + message.toString());
        // mailService.sendMail(message);
    }

    @VisibleForTesting
    protected SimpleMailMessage getNoResponseReminderMessage(final Event event, final String eventLink) {
        SimpleMailMessage message = new SimpleMailMessage();

        final Map<String, Object> model = getEventDetailsModel(event, eventLink);
        String eventDetails = templateService.mergeTemplate(EmailTemplate.EVENT_DETAILS.getTemplate(), model);
        model.put("eventDetails", eventDetails);

        message.setSubject(templateService.mergeTemplate(EmailTemplate.NORESPONSE_REMINDER.getSubject(), model));
        message.setText(templateService.mergeTemplate(EmailTemplate.NORESPONSE_REMINDER.getContent(), model));

        return message;
    }

    @Override
    public void sendUnsureReminder(final Event event, final List<Invitation> invitations) {
        log.info("Sending unsure reminder to [{}]", invitations);
        String eventLink = linkProvider.deepLinkEvent(event.getId());
        SimpleMailMessage message = getUnsureReminderMessage(event, eventLink);

        List<String> to = Lists.newArrayList();
        for (Invitation invitation : invitations) {
            if (invitation.hasEmail()) {
                to.addAll(Arrays.asList(invitation.getEmails()));
            }
        }
        message.setBcc(to.toArray(new String[]{}));

        log.warn("Reminder-Mails are currently mocked.\nSending mail:\n" + message.toString());
        // mailService.sendMail(message);
    }

    @VisibleForTesting
    protected SimpleMailMessage getUnsureReminderMessage(final Event event, final String eventLink) {
        SimpleMailMessage message = new SimpleMailMessage();

        final Map<String, Object> model = getEventDetailsModel(event, eventLink);
        String eventDetails = templateService.mergeTemplate(EmailTemplate.EVENT_DETAILS.getTemplate(), model);
        model.put("eventDetails", eventDetails);

        message.setSubject(templateService.mergeTemplate(EmailTemplate.UNSURE_REMINDER.getSubject(), model));
        message.setText(templateService.mergeTemplate(EmailTemplate.UNSURE_REMINDER.getContent(), model));

        return message;
    }

    @VisibleForTesting
    protected Map<String, Object> getEventDetailsModel(Event event, final String eventLink) {
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
        return model;
    }
}
