package de.flower.rmt.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author flowerrrr
 */
@Component
public class ReminderTask {

    private final static Logger log = LoggerFactory.getLogger(ReminderTask.class);

    // @Scheduled(cron = "0 0 6 * * ?")  // run at 6 in the morning
    @Scheduled(cron = "*/5 * * * * ?")
    public void sendReminderMails() {
        log.info("Running job");
    }
}
