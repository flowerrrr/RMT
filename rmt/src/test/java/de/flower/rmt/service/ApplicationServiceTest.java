package de.flower.rmt.service;

import de.flower.rmt.test.AbstractRMTIntegrationTests;
import org.testng.annotations.Test;


public class ApplicationServiceTest extends AbstractRMTIntegrationTests {

    @Test
    public void testGetMessageOfTheDay() {
        String motd = applicationService.getMessageOfTheDay();
        log.info(motd);

    }
}
