package de.flower.rmt.task;

import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.service.IInvitationManager;
import de.flower.rmt.service.mail.INotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author flowerrrr
 */
@Component
public class ReminderTask {

    private final static Logger log = LoggerFactory.getLogger(ReminderTask.class);

    @Autowired
    private IInvitationManager invitationManager;

    @Autowired
    private INotificationService notificationService;

    @Autowired
    private IEventManager eventManager;

    // @Scheduled(cron = "0 0 6 * * ?")  // run at 6 in the morning
    @Scheduled(cron = "0 * * * * ?")
    public void sendReminderMails() {
        log.info("Running job [sendReminderMails]");
        sendNoResponseReminder();
        sendUnsureReminder();
    }

    public void sendNoResponseReminder() {
        // select events eligible for reminding users
        List<Event> events = eventManager.findAllNextNHours(5 * 24);

        for (Event event : events) {
            log.info("Checking no response reminders for [{}].", event);
            List<Invitation> invitations = invitationManager.findAllForNoResponseReminder(event);
            notificationService.sendNoResponseReminder(invitations);
            invitationManager.markNoResponseReminderSent(invitations);
        }
    }

    public void sendUnsureReminder() {

    }
}
