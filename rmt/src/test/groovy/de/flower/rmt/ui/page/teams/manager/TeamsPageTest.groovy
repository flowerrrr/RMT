package de.flower.rmt.ui.page.teams.manager

import de.flower.rmt.test.AbstractRMTWicketIntegrationTests
import org.testng.annotations.Test

/**
 *
 * @author flowerrrr
 */

class TeamsPageTest extends AbstractRMTWicketIntegrationTests {

    @Test
    def void testRender() {
        wicketTester.startPage(new TeamsPage())
        wicketTester.dumpPage()
    }
}