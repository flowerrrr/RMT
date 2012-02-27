package de.flower.rmt.ui.common.page.event;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import de.flower.rmt.test.TestData;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */
public class EventPagerPanelTest extends AbstractRMTWicketMockitoTests {

    @SpringBean
    private IEventManager eventManager;

    @Test
    public void testRender() {
        Event event = new TestData().newEvent();
        Event nextEvent = new TestData().newEvent();
        List<Event> events = Arrays.asList(event, nextEvent);
        wicketTester.setSerializationCheck(false);
        wicketTester.startComponentInPage(new EventPagerPanel(Model.of(event), (IModel) Model.ofList(events)){

            @Override
            protected void onClick(final IModel<Event> model) {

            }
        });
        wicketTester.dumpComponentWithPage();
        Component date = wicketTester.getComponentFromLastRenderedPage("eventPagerPanel:date");
        assertEquals(event.getDate(), date.getDefaultModelObject());
    }

}
