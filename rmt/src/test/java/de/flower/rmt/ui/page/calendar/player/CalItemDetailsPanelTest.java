package de.flower.rmt.ui.page.calendar.player;

import de.flower.rmt.model.db.entity.CalItem;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */
public class CalItemDetailsPanelTest extends AbstractRMTWicketMockitoTests {

    @SpringBean
    private IEventManager eventManager;

    @Test
    public void testRender() {
        CalItem calItem = testData.newCalItem();
        wicketTester.startComponentInPage(new CalItemDetailsPanel("calendarSecondaryPanel", Model.of(calItem)));
        wicketTester.dumpComponentWithPage();
        // assert RMT-690 is fixed
        assertTrue(!calItem.isAllDay());
        wicketTester.assertContainsNot("AM");
        wicketTester.assertContainsNot("PM");
    }
}
