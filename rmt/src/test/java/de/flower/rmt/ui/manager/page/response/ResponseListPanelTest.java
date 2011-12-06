package de.flower.rmt.ui.manager.page.response;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.test.AbstractWicketTests;
import de.flower.rmt.ui.model.EventModel;

/**
 * @author flowerrrr
 */
public class ResponseListPanelTest extends AbstractWicketTests {

    @Override
    public void testRender() {
        Event event = testData.createEventWithResponses();
        wicketTester.startComponentInPage(new ResponseListPanel(new EventModel(event)));
        wicketTester.dumpComponentWithPage();
    }
}
