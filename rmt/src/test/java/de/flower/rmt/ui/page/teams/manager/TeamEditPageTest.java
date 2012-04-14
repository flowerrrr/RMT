package de.flower.rmt.ui.page.teams.manager;

import de.flower.rmt.test.AbstractWicketIntegrationTests;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */

public class TeamEditPageTest extends AbstractWicketIntegrationTests {

    @Test
    public void testRender() {
        wicketTester.startPage(new TeamEditPage()) ;
        wicketTester.dumpPage() ;
    }
}