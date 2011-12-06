package de.flower.rmt.ui.manager.page.players

import de.flower.rmt.test.AbstractWicketTests
import org.testng.annotations.Test
/**
 * 
 * @author flowerrrr
 */

class PlayersPageTest extends AbstractWicketTests {

    @Test
    def testRender() {
        wicketTester.startPage(new PlayersPage())
        wicketTester.dumpPage()
    }


}