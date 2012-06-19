package de.flower.rmt.ui.page.calendar.player;

import de.flower.rmt.model.db.entity.CalItem;
import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import org.apache.wicket.model.Model;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class CalItemEditPanelTest extends AbstractRMTWicketMockitoTests {

    @Test
    public void testRender() {
        CalItem calItem = testData.newCalItem();
        wicketTester.startComponentInPage(new CalItemEditPanel(Model.of(calItem)));
        wicketTester.dumpComponentWithPage();
    }

}
