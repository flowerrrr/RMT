package de.flower.rmt.ui.page.squad.manager

import de.flower.rmt.test.AbstractWicketIntegrationTests
import org.testng.annotations.Test
import de.flower.rmt.ui.model.TeamModel

/**
 * 
 * @author flowerrrr
 */

class SquadPageTest extends AbstractWicketIntegrationTests {

    @Test
    def testRender() {
        val team = testData.getJuveAmateure();
        wicketTester.startPage(new SquadPage(new TeamModel(team)))
        wicketTester.dumpPage()
        wicketTester.debugComponentTrees()
        wicketTester.clickLink("secondaryPanel:squadSecondaryPanel:addPlayerPanel:toggleButton")
        wicketTester.dumpPage()
    }


}