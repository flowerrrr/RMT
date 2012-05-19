package de.flower.rmt.ui.page.event;

import de.flower.rmt.model.db.entity.event.Match;
import de.flower.rmt.model.db.type.EventType;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import de.flower.rmt.ui.app.View;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.mockito.Matchers;
import org.testng.annotations.Test;

import javax.persistence.metamodel.Attribute;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * @author flowerrrr
 */
public class EventDetailsPanelTest extends AbstractRMTWicketMockitoTests {

    @SpringBean
    private IEventManager eventManager;

    @Test
    public void testRender() {
        testData.setEventType(EventType.Match);
        Match event = (Match) testData.newEvent();
        event.setId(100L);
        when(eventManager.loadById(anyLong(), Matchers.<Attribute>anyVararg())).thenReturn(event);
        wicketTester.startComponentInPage(new EventDetailsPanel(Model.of(event), View.MANAGER));
        wicketTester.dumpComponentWithPage();
        wicketTester.assertContains(event.getSummary());
        wicketTester.clickLink("eventDetailsPanel:venue");

        event.setUniform(null);
        event.setVenue(null);
        wicketTester.startComponentInPage(new EventDetailsPanel(Model.of(event), View.PLAYER));
        wicketTester.dumpComponentWithPage();
    }
}
