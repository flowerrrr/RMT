package de.flower.rmt.service.mail;

import de.flower.rmt.model.Surface;
import de.flower.rmt.model.User;
import de.flower.rmt.model.event.*;
import de.flower.rmt.model.type.Notification;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.util.Dates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public void sendResetPasswordMail(final User user) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("user", user);
        String subject = templateService.mergeTemplate(EmailTemplate.PASSWORD_RESET.getSubject(), model);
        String content = templateService.mergeTemplate(EmailTemplate.PASSWORD_RESET.getContent(), model);
        mailService.sendMail(user.getEmail(), subject, content);
    }

    @Override
    public void sendInvitationNewUser(final User user, final User manager) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("user", user);
        model.put("manager", manager);
        model.put("club", user.getClub());
        String subject = templateService.mergeTemplate(EmailTemplate.INVITATION_NEWUSER.getSubject(), model);
        String content = templateService.mergeTemplate(EmailTemplate.INVITATION_NEWUSER.getContent(), model);
        mailService.sendMail(user.getEmail(), subject, content);
    }

    @Override
    public Notification newEventNotification(final Event eventIn, String eventLink) {
        Event event = eventManager.loadById(eventIn.getId(), Event_.venue, Event_.team, AbstractSoccerEvent_.uniform, Match_.opponent);
        Notification notification = new Notification();
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("event", event);
        model.put("eventDate", Dates.formatDateMedium(event.getDate()));
        model.put("eventTime", Dates.formatTimeShort(event.getTime()));
        model.put("eventDateTime", Dates.formatDateTimeShort(event.getDateTime()));
        model.put("eventTypeMatch", EventType.Match);
        model.put("eventLink", eventLink);
        model.put("isSoccerEvent", EventType.isSoccerEvent(event));
        if (EventType.isSoccerEvent(event)) {
            AbstractSoccerEvent soccerEvent = (AbstractSoccerEvent) event;
            model.put("kickoffTime", Dates.formatTimeShort(soccerEvent.getKickoff()));
            model.put("surfaceList", Surface.render(soccerEvent.getSurfaceList()));
        }
        notification.setSubject(templateService.mergeTemplate(EmailTemplate.NOTIFICATION_EVENT.getSubject(), model));
        notification.setBody(templateService.mergeTemplate(EmailTemplate.NOTIFICATION_EVENT.getContent(), model));
        return notification;
    }
}
