package de.flower.rmt.ui.page.event.player;

import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import org.apache.wicket.model.Model;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class LineupPanelTest extends AbstractRMTWicketMockitoTests {

    @Test
    public void testRender() {
        Event event = testData.newEvent();
        wicketTester.startComponentInPage(new LineupPanel("panel", Model.of(event)));
        wicketTester.dumpComponentWithPage();
    }

}
