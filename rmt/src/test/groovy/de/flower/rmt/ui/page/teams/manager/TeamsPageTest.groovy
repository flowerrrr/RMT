package de.flower.rmt.ui.page.teams.manager

import de.flower.rmt.test.AbstractWicketIntegrationTests
import org.testng.annotations.Test

/**
 *
 * @author flowerrrr
 */

class TeamsPageTest extends AbstractWicketIntegrationTests {

    @Test
    def void testRender() {
        wicketTester.startPage(new TeamsPage())
        wicketTester.dumpPage()
    }
}