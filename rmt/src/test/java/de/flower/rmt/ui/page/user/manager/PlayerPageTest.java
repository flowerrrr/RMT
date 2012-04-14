package de.flower.rmt.ui.page.user.manager;

import de.flower.rmt.test.AbstractWicketIntegrationTests;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */

public class PlayerPageTest extends AbstractWicketIntegrationTests {

    @Test
    public void testRender() {
        wicketTester.startPage(new PlayerPage());
        wicketTester.dumpPage();
        wicketTester.assertInvisible("sendInvitationPanel");
        wicketTester.assertInvisible("resetPasswordPanel");
    }
}