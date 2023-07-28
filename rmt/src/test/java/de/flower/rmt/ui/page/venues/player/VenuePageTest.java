package de.flower.rmt.ui.page.venues.player;

import de.flower.rmt.model.db.entity.Venue;
import de.flower.rmt.test.AbstractRMTWicketIntegrationTests;
import de.flower.rmt.ui.model.VenueModel;
import org.testng.annotations.Test;


public class VenuePageTest  extends AbstractRMTWicketIntegrationTests {

    @Test
    public void testRender() {
        Venue venue = testData.createVenue(testData.getClub());
        wicketTester.startPage(new VenuePage(new VenueModel(venue)));
        wicketTester.dumpPage();
    }
}
