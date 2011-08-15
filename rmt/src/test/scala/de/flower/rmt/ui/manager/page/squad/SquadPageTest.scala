package de.flower.rmt.ui.manager.page.squad

import de.flower.rmt.test.WicketTests
import org.testng.annotations.Test
import de.flower.rmt.ui.manager.page.teams.TeamsPage
import de.flower.rmt.ui.manager.page.squad.SquadPage
import org.apache.wicket.model.Model

/**
 * 
 * @author oblume
 */

class SquadPageTest extends WicketTests {

    @Test
    def renderPage() {
        val team = testData.getJuveAmateure();
        wicketTester.startPage(new SquadPage(Model.of(team)))
        wicketTester.dumpPage()
        wicketTester.clickLink("addButton")
        wicketTester.dumpPage()
    }


}