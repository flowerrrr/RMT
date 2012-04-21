package de.flower.rmt.service;

import de.flower.rmt.test.AbstractIntegrationTests;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class ApplicationServiceTest extends AbstractIntegrationTests {

    @Test
    public void testGetMessageOfTheDay() {
        String motd = applicationService.getMessageOfTheDay();
        log.info(motd);

    }
}
