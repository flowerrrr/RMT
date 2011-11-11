package de.flower.rmt.ui.manager.page.teams

import de.flower.rmt.test.WicketTests
import org.testng.annotations.Test
import de.flower.rmt.ui.model.TeamModel

/**
 * 
 * @author flowerrrr
 */

class TeamEditPanelTest extends WicketTests {

    @Test
    def validateUniquenessConstraint() {
        wicketTester.startComponentInPage(new TeamEditPanel(new TeamModel()))
        wicketTester.dumpPage()
        wicketTester.debugComponentTrees()
        // input name and validate field
        val field = wicketTester.getComponentFromLastRenderedPage("form:name:name")
        wicketTester.assertValidation(field, "teamname", true)
        // set teamname to existing team and revalidate field
        wicketTester.assertValidation(field, "Juve Amateure", false)
    }


}