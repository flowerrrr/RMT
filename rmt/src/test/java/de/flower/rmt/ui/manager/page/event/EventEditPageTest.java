package de.flower.rmt.ui.manager.page.event;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.EventType;
import de.flower.rmt.test.AbstractWicketIntegrationTests;
import org.apache.wicket.model.Model;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */

public class EventEditPageTest extends AbstractWicketIntegrationTests {

    @Test
    public void testRender() {
        Event event = eventManager.newInstance(EventType.Training);
        wicketTester.startPage(new EventEditPage(Model.of(event)));
        wicketTester.dumpPage();
    }

}