package de.flower.rmt.ui.manager.page.squad

import de.flower.rmt.test.AbstractWicketTests
import org.testng.annotations.Test
import de.flower.rmt.ui.model.TeamModel

/**
 * 
 * @author flowerrrr
 */

class SquadPageTest extends AbstractWicketTests {

    @Test
    def testRender() {
        val team = testData.getJuveAmateure();
        wicketTester.startPage(new SquadPage(new TeamModel(team)))
        wicketTester.dumpPage()
        wicketTester.debugComponentTrees()
        wicketTester.clickLink("secondaryPanel:squadSecondaryPanel:addButton")
        wicketTester.dumpPage()
    }


}