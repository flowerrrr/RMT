package de.flower.rmt.ui.page.users.manager

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