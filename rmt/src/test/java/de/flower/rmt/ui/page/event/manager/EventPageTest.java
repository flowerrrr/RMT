package de.flower.rmt.ui.page.event.manager;

import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.entity.event.EventType;
import de.flower.rmt.test.AbstractRMTWicketIntegrationTests;
import de.flower.rmt.ui.model.EventModel;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */

public class EventPageTest extends AbstractRMTWicketIntegrationTests {

    @Test
    public void testRender() {
        Event event = eventManager.newInstance(EventType.Training);
        wicketTester.startPage(new EventPage(new EventModel(event)));
        wicketTester.dumpPage();
    }

}