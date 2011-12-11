package de.flower.rmt.ui.manager.page.player;

import de.flower.rmt.test.AbstractWicketTests;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */

public class PlayerEditPageTest extends AbstractWicketTests {

    @Test
    public void testRender() {
        wicketTester.startPage(new PlayerEditPage());
        wicketTester.dumpPage();
        wicketTester.assertInvisible("sendInvitationPanel");
        wicketTester.assertInvisible("resetPasswordPanel");
    }
}