package de.flower.rmt.ui.manager.page.teams;

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