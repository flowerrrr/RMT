package de.flower.rmt.task;

import de.flower.common.util.Check;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.security.SecurityService;
import de.flower.rmt.service.EventManager;
import de.flower.rmt.service.InvitationManager;
import de.flower.rmt.service.mail.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author flowerrrr
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ReminderTask {

    private final static Logger log = LoggerFactory.getLogger(ReminderTask.class);

    @Autowired
    private InvitationManager invitationManager;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private EventManager eventManager;

    @Value("${reminder.noresponse.days.before.event}")
    private Integer noResponseDaysBeforeEvent;

    @Value("${reminder.noresponse.hours.after.invitation}")
    private Integer hoursAfterInvitationSent;

    @Value("${reminder.unsure.hours.before.event}")
    private Integer unsureReminderHoursBeforeEvent;

    public void sendNoResponseReminder() {
        checkPreconditions();

        // select events eligible for reminding users
        List<Event> events = eventManager.findAllNextNHours(noResponseDaysBeforeEvent * 24);

        for (Event event : events) {
            log.info("Checking no response reminders for [{}].", event);
            List<Invitation> invitations = invitationManager.findAllForNoResponseReminder(event, hoursAfterInvitationSent);
            if (!invitations.isEmpty()) {
                // first mark as sent to avoid repeated sending of mails if marking fails
                invitationManager.markNoResponseReminderSent(invitations);
                notificationService.sendNoResponseReminder(event, invitations);
            }
        }
    }

    public void sendUnsureReminder() {
        checkPreconditions();
        // select events eligible for reminding users
        List<Event> events = eventManager.findAllNextNHours(unsureReminderHoursBeforeEvent);

        for (Event event : events) {
            log.info("Checking unsure response reminders for [{}].", event);
            List<Invitation> invitations = invitationManager.findAllForUnsureReminder(event);
            if (!invitations.isEmpty()) {
                // first mark as sent to avoid repeated sending of mails if marking fails
                invitationManager.markUnsureReminderSent(invitations);
                notificationService.sendUnsureReminder(event, invitations);
            }
        }
    }

    /**
     * Since the task execution is not run inside wicket-request context there are some vital parts
     * that the scheduler must setup.
     */
    private void checkPreconditions() {
        // dao-calls need access to current club.
        Check.notNull(securityService.getUser().getClub());
    }
}
