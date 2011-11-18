package de.flower.rmt.ui.manager.page.squad

import de.flower.rmt.test.WicketTests
import org.testng.annotations.Test
import de.flower.rmt.ui.model.TeamModel

/**
 * 
 * @author flowerrrr
 */

class SquadPageTest extends WicketTests {

    @Test
    def renderPage() {
        val team = testData.getJuveAmateure();
        wicketTester.startPage(new SquadPage(new TeamModel(team)))
        wicketTester.dumpPage()
        wicketTester.debugComponentTrees()
        wicketTester.clickLink("secondaryPanel:squadSecondaryPanel:addButton")
        wicketTester.dumpPage()
    }


}