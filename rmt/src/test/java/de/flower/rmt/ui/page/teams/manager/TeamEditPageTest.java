package de.flower.rmt.ui.page.teams.manager;

import de.flower.rmt.test.AbstractRMTWicketIntegrationTests;
import org.testng.annotations.Test;



public class TeamEditPageTest extends AbstractRMTWicketIntegrationTests {

    @Test
    public void testRender() {
        wicketTester.startPage(new TeamEditPage()) ;
        wicketTester.dumpPage() ;
    }
}