package de.flower.rmt.ui.manager.page.teams

import de.flower.rmt.test.AbstractWicketTests
import org.testng.annotations.Test

/**
 *
 * @author flowerrrr
 */

class TeamsPageTest extends AbstractWicketTests {

    @Test
    def testRender() {
        wicketTester.startPage(new TeamsPage())
        wicketTester.dumpPage()
    }
}