package de.flower.rmt.ui.page.user.manager;

import de.flower.rmt.test.AbstractRMTWicketIntegrationTests;
import org.testng.annotations.Test;



public class PlayerPageTest extends AbstractRMTWicketIntegrationTests {

    @Test
    public void testRender() {
        wicketTester.startPage(new PlayerPage());
        wicketTester.dumpPage();
        wicketTester.assertInvisible("sendInvitationPanel");
        wicketTester.assertInvisible("resetPasswordPanel");
    }
}