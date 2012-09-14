package de.flower.rmt.task;


import de.flower.rmt.test.AbstractRMTIntegrationTests
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration
import org.testng.annotations.Test

/**
 * @author flowerrrr
 */
@groovy.transform.TypeChecked
@ContextConfiguration(classes = [Config.class])
public class TaskSchedulerTest extends AbstractRMTIntegrationTests {

    @Configuration
    @ComponentScan(basePackages = ["de.flower.rmt.task"])
    public static class Config {
    }

    @Autowired
    private TaskScheduler taskScheduler;

    @Test
    public void testSendReminderMails() {
        securityContextHolderStrategy.clearContext();
        taskScheduler.sendReminderMails();
    }
}
