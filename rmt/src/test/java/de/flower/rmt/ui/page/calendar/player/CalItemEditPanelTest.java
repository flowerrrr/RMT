package de.flower.rmt.ui.page.calendar.player;

import de.flower.rmt.model.dto.CalItemDto;
import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import org.apache.wicket.model.Model;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class CalItemEditPanelTest extends AbstractRMTWicketMockitoTests {

    @Test
    public void testRender() {
        CalItemDto dto = testData.newCalItemDto();
        wicketTester.startComponentInPage(new CalItemEditPanel(Model.of(dto), false));
        wicketTester.dumpComponentWithPage();
    }

}
