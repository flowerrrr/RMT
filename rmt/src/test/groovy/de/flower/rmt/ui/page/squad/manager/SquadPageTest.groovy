package de.flower.rmt.ui.page.squad.manager

import de.flower.rmt.test.AbstractRMTWicketIntegrationTests
import de.flower.rmt.ui.model.TeamModel
import org.testng.annotations.Test

/**
 *
 * @author flowerrrr
 */

class SquadPageTest extends AbstractRMTWicketIntegrationTests {

    @Test
    def void testRender() {
        def team = testData.getJuveAmateure();
        wicketTester.startPage(new SquadPage(new TeamModel(team)))
        wicketTester.dumpPage()
        wicketTester.debugComponentTrees()
        wicketTester.clickLink("secondaryPanel:squadSecondaryPanel:addPlayerPanel:toggleButton")
        wicketTester.dumpPage()
    }
}