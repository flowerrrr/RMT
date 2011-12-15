package de.flower.rmt.ui.manager.page.player;

import de.flower.rmt.model.User;
import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import org.apache.wicket.model.Model;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */

public class PlayerGeneralPanelTest extends AbstractRMTWicketMockitoTests {

    @Test
    public void testRender() {
        User user = testData.newUser();
        wicketTester.startComponentInPage(new PlayerGeneralPanel("panel", Model.of(user)));
        wicketTester.dumpComponentWithPage();
    }

}