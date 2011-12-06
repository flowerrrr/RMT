package de.flower.rmt.ui.manager.page.teams;

import de.flower.rmt.test.AbstractWicketTests;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */

public class TeamEditPageTest extends AbstractWicketTests {

    @Test
    public void testRender() {
        wicketTester.startPage(new TeamEditPage()) ;
        wicketTester.dumpPage() ;
    }
}