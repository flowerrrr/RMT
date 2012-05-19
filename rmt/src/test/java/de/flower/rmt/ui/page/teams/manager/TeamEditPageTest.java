package de.flower.rmt.ui.page.teams.manager;

import de.flower.rmt.test.AbstractRMTWicketIntegrationTests;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */

public class TeamEditPageTest extends AbstractRMTWicketIntegrationTests {

    @Test
    public void testRender() {
        wicketTester.startPage(new TeamEditPage()) ;
        wicketTester.dumpPage() ;
    }
}