package de.flower.rmt.ui.manager.page.teams

import de.flower.rmt.test.WicketTests
import org.testng.annotations.Test

/**
 * 
 * @author flowerrrr
 */

class TeamEditPageTest extends WicketTests {

    @Test
    def renderPage() {
        wicketTester.startPage(new TeamEditPage())
        wicketTester.dumpPage()
    }


}