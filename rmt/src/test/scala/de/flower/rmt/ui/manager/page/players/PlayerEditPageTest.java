package de.flower.rmt.ui.manager.page.players;

import de.flower.rmt.test.AbstractWicketTests;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */

class PlayerEditPageTest extends AbstractWicketTests {

    @Test
    public void testRender() {
        wicketTester.startPage(new PlayerEditPage());
        wicketTester.dumpPage();
    }
}