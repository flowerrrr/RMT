package de.flower.rmt.ui.page.user.manager;

import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import org.apache.wicket.model.Model;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class PlayerMainPanelTest extends AbstractRMTWicketMockitoTests {

    @Test
    public void testRender() {
        wicketTester.startComponentInPage(new PlayerMainPanel(Model.of(testData.newUser())));
        wicketTester.dumpComponentWithPage();
    }

}
