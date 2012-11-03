package de.flower.rmt.service.mail;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.flower.common.util.Check;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.Uniform;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.db.entity.event.AbstractSoccerEvent;
import de.flower.rmt.model.db.entity.event.AbstractSoccerEvent_;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.entity.event.EventType;
import de.flower.rmt.model.db.entity.event.Event_;
import de.flower.rmt.model.db.entity.event.Match_;
import de.flower.rmt.model.db.type.RSVPStatus;
import de.flower.rmt.model.dto.Notification;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.service.IICalendarProvider;
import de.flower.rmt.service.IInvitationManager;
import de.flower.rmt.service.IUrlProvider;
import de.flower.rmt.ui.markup.html.form.renderer.SurfaceRenderer;
import de.flower.rmt.util.Dates;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
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
public class NotificationService implements INotificationService, IICalendarProvider {

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
    private IUrlProvider urlProvider;

    @Autowired
    private MessageSourceAccessor messageSource;

    @Override
    public void sendResetPasswordMail(final User user, final User manager) {
        mailService.sendMail(getResetPasswordMessage(user, manager));
    }

    @VisibleForTesting
    protected SimpleMailMessage getResetPasswordMessage(final User user, final User manager) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmails());
        if (manager != null) {
            message.setBcc(manager.getEmail());
        }

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
    // required to enable lazy fetching of event and event.created by user
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
        model.put("eventType", messageSource.getMessage(event.getEventType().getResourceKey()));
        model.put("status", messageSource.getMessage(RSVPStatus.getResourceKey(invitation.getStatus())));
        String subject = templateService.mergeTemplate(EmailTemplate.INVITATION_STATUSCHANGED.getSubject(), model);
        String content = templateService.mergeTemplate(EmailTemplate.INVITATION_STATUSCHANGED.getContent(), model);

        message.setSubject(subject);
        message.setText(content);
        return message;
    }

    @Override
    public Notification newEventNotification(final Event eventIn) {
        Event event = eventManager.loadById(eventIn.getId(), Event_.venue, Event_.team, AbstractSoccerEvent_.uniform, Match_.opponent);
        return getNewEventNotification(event);
    }

    @VisibleForTesting
    protected Notification getNewEventNotification(Event event) {

        Notification notification = new Notification();

        final Map<String, Object> model = getEventDetailsModel(event);
        String eventDetails = templateService.mergeTemplate(EmailTemplate.EVENT_DETAILS.getTemplate(), model);
        model.put("eventDetails", eventDetails);

        notification.setSubject(templateService.mergeTemplate(EmailTemplate.NOTIFICATION_EVENT.getSubject(), model));
        notification.setBody(templateService.mergeTemplate(EmailTemplate.NOTIFICATION_EVENT.getContent(), model));

        // create iCalendar attachment
        notification.setAttachment(getCalendarAttachment(event));

        return notification;
    }

    @Override
    public String getICalendar(final Event event) {
        final Map<String, Object> model = Maps.newHashMap();
        ICalendarHelper iCalendarHelper = new ICalendarHelper(event);
        model.put("uid", iCalendarHelper.getUid());
        model.put("dtstart", iCalendarHelper.getDtstart());
        model.put("dtend", iCalendarHelper.getDtend());
        model.put("dtstamp", iCalendarHelper.getDtstamp());
        model.put("summary", iCalendarHelper.getSummary());
        model.put("description", iCalendarHelper.getDescription(getEventDetails(event)));
        model.put("location", iCalendarHelper.getLocation());

        String iCalender = templateService.mergeTemplate(EmailTemplate.EVENT_ICALENDAR.getTemplate(), model);
        return iCalender;
    }

    @VisibleForTesting
    protected Notification.Attachment getCalendarAttachment(Event event) {

        Notification.Attachment attachment = new Notification.Attachment();
        attachment.name = messageSource.getMessage("icalendar.name");
        attachment.contentType = ICalendarHelper.CONTENT_TYPE_MAIL;

        attachment.data = ICalendarHelper.getBytes(getICalendar(event));
        return attachment;
    }

    @Override
    public void sendNoResponseReminder(Event event, final List<Invitation> invitations) {
        Check.isTrue(!event.isCanceled(), "Trying to send reminder mail for canceled event.");
        log.info("Sending no-response reminder to [{}]", invitations);
        SimpleMailMessage message = getNoResponseReminderMessage(event);

        List<String> to = Lists.newArrayList();
        for (Invitation invitation : invitations) {
            if (invitation.hasEmail()) {
                to.addAll(Arrays.asList(invitation.getEmails()));
            } else {
                log.info("Cannot send reminder for invitation without email address [{}].", invitation);
            }
        }

        if (to.isEmpty()) {
            log.warn("#sendNoResponseReminder() called with inviations that have no email address assigned.");
            return;
        }

        message.setBcc(to.toArray(new String[]{}));

        mailService.sendMail(message);

        // send mail to manager
        sendSummaryToManager(event, to, "No response to invitation mail.");
    }

    private void sendSummaryToManager(final Event event, final List<String> to, final String reason) {
        final SimpleMailMessage message;
        message = new SimpleMailMessage();
        message.setTo(event.getCreatedBy().getEmail());
        message.setSubject("das tool: Reminder-Mail summary");

        String body = "An auto-reminder-mail was sent to the following recpients.\n"
                + "Event: " + urlProvider.deepLinkEvent(event.getId()) + "\n"
                + "Reason: " + reason + "\n"
                + "Users:\n";
        body += StringUtils.join(to, "\n");
        body += "\n"
                + "_____________________________________________________\n"
                + "Sent by das-tool reminder task.";
        message.setText(body);

        mailService.sendMail(message);
    }

    @VisibleForTesting
    protected SimpleMailMessage getNoResponseReminderMessage(final Event event) {
        SimpleMailMessage message = new SimpleMailMessage();

        final Map<String, Object> model = getEventDetailsModel(event);
        String eventDetails = templateService.mergeTemplate(EmailTemplate.EVENT_DETAILS.getTemplate(), model);
        model.put("eventDetails", eventDetails);

        message.setSubject(templateService.mergeTemplate(EmailTemplate.NORESPONSE_REMINDER.getSubject(), model));
        message.setText(templateService.mergeTemplate(EmailTemplate.NORESPONSE_REMINDER.getContent(), model));

        return message;
    }

    @Override
    public void sendUnsureReminder(final Event event, final List<Invitation> invitations) {
        Check.isTrue(!event.isCanceled(), "Trying to send reminder mail for canceled event.");
        log.info("Sending unsure reminder to [{}]", invitations);
        SimpleMailMessage message = getUnsureReminderMessage(event);

        List<String> to = Lists.newArrayList();
        for (Invitation invitation : invitations) {
            if (invitation.hasEmail()) {
                to.addAll(Arrays.asList(invitation.getEmails()));
            } else {
                log.info("Cannot send reminder for invitation without email address [{}].", invitation);
            }
        }

        if (to.isEmpty()) {
            log.warn("#sendUnsureReminder() called with inviations that have no email address assigned.");
            return;
        }

        message.setBcc(to.toArray(new String[]{}));

        mailService.sendMail(message);

        // send mail to manager
        sendSummaryToManager(event, to, "Response status set to 'maybe'.");
    }

    @VisibleForTesting
    protected SimpleMailMessage getUnsureReminderMessage(final Event event) {
        SimpleMailMessage message = new SimpleMailMessage();

        final Map<String, Object> model = getEventDetailsModel(event);
        String eventDetails = templateService.mergeTemplate(EmailTemplate.EVENT_DETAILS.getTemplate(), model);
        model.put("eventDetails", eventDetails);

        message.setSubject(templateService.mergeTemplate(EmailTemplate.UNSURE_REMINDER.getSubject(), model));
        message.setText(templateService.mergeTemplate(EmailTemplate.UNSURE_REMINDER.getContent(), model));

        return message;
    }

    @Override
    public void sendEventCanceledMessage(final Event event, final List<Invitation> invitations) {
        log.info("Sending event canceled notification to [{}]", invitations);
        SimpleMailMessage message = getEventCanceledMessage(event);

        List<String> to = Lists.newArrayList();
        for (Invitation invitation : invitations) {
            if (invitation.hasEmail()) {
                to.addAll(Arrays.asList(invitation.getEmails()));
            }
        }
        message.setBcc(to.toArray(new String[]{}));

        mailService.sendMail(message);
    }

    @VisibleForTesting
    protected SimpleMailMessage getEventCanceledMessage(final Event event) {
        SimpleMailMessage message = new SimpleMailMessage();

        final Map<String, Object> model = getEventDetailsModel(event);
        String eventDetails = templateService.mergeTemplate(EmailTemplate.EVENT_DETAILS.getTemplate(), model);
        model.put("eventDetails", eventDetails);

        message.setSubject(templateService.mergeTemplate(EmailTemplate.EVENT_CANCELED.getSubject(), model));
        message.setText(templateService.mergeTemplate(EmailTemplate.EVENT_CANCELED.getContent(), model));

        return message;
    }

    protected String getEventDetails(Event event) {
        final Map<String, Object> model = getEventDetailsModel(event);
        String eventDetails = templateService.mergeTemplate(EmailTemplate.EVENT_DETAILS.getTemplate(), model);
        return eventDetails;
    }

    @VisibleForTesting
    protected Map<String, Object> getEventDetailsModel(Event event) {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("event", event);
        model.put("eventDate", Dates.formatDateMediumWithWeekday(event.getDateTimeAsDate()));
        model.put("eventTime", Dates.formatTimeShort(event.getDateTimeAsDate()));
        model.put("eventDateTime", Dates.formatDateTimeShortWithWeekday(event.getDateTimeAsDate()));
        model.put("eventType", messageSource.getMessage(event.getEventType().getResourceKey()));
        model.put("eventTypeMatch", EventType.Match);
        model.put("eventLink", urlProvider.deepLinkEvent(event.getId()));

        model.put("isSoccerEvent", EventType.isSoccerEvent(event));
        if (event.getVenue() != null) {
            model.put("directionsLink", urlProvider.getDirectionsUrl(event.getVenue().getLatLng()));
        }
        if (EventType.isSoccerEvent(event)) {
            AbstractSoccerEvent soccerEvent = (AbstractSoccerEvent) event;
            model.put("kickoffTime", Dates.formatTimeShort(soccerEvent.getKickoff()));
            model.put("surfaceList", new SurfaceRenderer() {
                @Override
                protected String getResourceString(final String key) {
                    return messageSource.getMessage(key);
                }
            }.renderList(soccerEvent.getSurfaceList()));
            if (soccerEvent.getUniform() != null) {
                Uniform u = soccerEvent.getUniform();
                final Object[] params = new Object[]{u.getShirt(), u.getShorts(), u.getSocks()};
                model.put("uniform", messageSource.getMessage("uniform.set", params));
            }
        }
        return model;
    }
}
