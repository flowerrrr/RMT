package de.flower.rmt.ui.manager.page.players

import de.flower.rmt.test.AbstractWicketIntegrationTests
import org.testng.annotations.Test
/**
 * 
 * @author flowerrrr
 */

class PlayersPageTest extends AbstractWicketIntegrationTests {

    @Test
    def testRender() {
        wicketTester.startPage(new PlayersPage())
        wicketTester.dumpPage()
    }


}