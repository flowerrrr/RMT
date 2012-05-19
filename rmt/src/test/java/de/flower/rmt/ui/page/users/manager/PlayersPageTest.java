package de.flower.rmt.ui.page.users.manager;

import de.flower.rmt.test.AbstractRMTWicketIntegrationTests;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */

public class PlayersPageTest extends AbstractRMTWicketIntegrationTests {

    @Test
    public void testRender() {
        wicketTester.startPage(new PlayersPage());
        wicketTester.dumpPage();
    }
}