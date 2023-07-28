package de.flower.rmt.ui.page.venues.player;

import de.flower.rmt.model.db.entity.Venue;
import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import org.apache.wicket.model.Model;
import org.testng.annotations.Test;


public class VenuePanelTest extends AbstractRMTWicketMockitoTests {

    @Test
    public void testRender() {
        Venue venue = testData.newVenue(testData.newClub());
        wicketTester.startComponentInPage(new VenuePanel(Model.of(venue)));
        wicketTester.dumpComponentWithPage();
    }

}
