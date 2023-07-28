package de.flower.rmt.ui.page.venues.manager;

import de.flower.rmt.test.AbstractRMTWicketIntegrationTests;
import org.testng.annotations.Test;


public class VenuesPageTest extends AbstractRMTWicketIntegrationTests {

    @Test
    public void testRender() {
        wicketTester.startPage(new VenuesPage());
        wicketTester.dumpPage();
    }
}