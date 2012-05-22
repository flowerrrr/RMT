package de.flower.rmt.task;

import de.flower.rmt.model.db.entity.Club;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.service.IClubManager;
import de.flower.rmt.service.security.UserDetailsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.intercept.RunAsUserToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author flowerrrr
 */
@Component
public class TaskScheduler {

    private final static Logger log = LoggerFactory.getLogger(TaskScheduler.class);

    /**
     * Run scheduled task every 10 minutes. Increase when feature becomes stable.
     */
    private final static long rate = 10 * 60 * 1000;

    @Autowired
    private SecurityContextHolderStrategy schs;

    @Autowired
    private IClubManager clubManager;

    @Autowired
    private ReminderTask reminderTask;

    @Scheduled(fixedRate = rate)
    public void sendReminderMails() throws InterruptedException {
        // delay execution for some seconds so that app startup is fully finished.
        // this is not required but makes reading the log-files easier.
        Thread.sleep(3000);
        log.info("Running job [sendReminderMails]");
        List<Club> clubs = clubManager.findAllClubs();
        for (Club club : clubs) {
            log.info("Checking reminders for [{}]", club);
            // must set security context with current club, so that dao-calls get filtered by correct club
            schs.getContext().setAuthentication(newAuthentication(club));
            reminderTask.sendNoResponseReminder();
            reminderTask.sendUnsureReminder();
        }
        log.info("Finished job");
    }

    private Authentication newAuthentication(final Club club) {
         User user = new User(club);
         user.setEmail("systemUser");
         user.setEncryptedPassword("");
         Object principal = new UserDetailsBean(user);
         return new RunAsUserToken("", principal, null, null, null);
     }


}
