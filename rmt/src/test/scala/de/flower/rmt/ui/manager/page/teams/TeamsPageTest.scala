package de.flower.rmt.ui.manager.page.teams

import de.flower.rmt.test.WicketTests
import org.testng.annotations.Test

/**
 * 
 * @author oblume
 */

class TeamsPageTest extends WicketTests {

    @Test
    def renderPage() {
        wicketTester.startPage(new TeamsPage())
        wicketTester.dumpPage()
    }


}