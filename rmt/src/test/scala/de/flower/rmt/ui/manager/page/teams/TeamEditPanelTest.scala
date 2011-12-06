package de.flower.rmt.ui.manager.page.teams

import de.flower.rmt.test.AbstractWicketTests
import org.testng.annotations.Test
import de.flower.rmt.ui.model.TeamModel

/**
 * 
 * @author flowerrrr
 */

class TeamEditPanelTest extends AbstractWicketTests {


    @Test
    def testRender() {
        wicketTester.startComponentInPage(new TeamEditPanel(new TeamModel()))
        wicketTester.dumpComponentWithPage()
    }

    @Test
    def validateUniquenessConstraint() {
        wicketTester.startComponentInPage(new TeamEditPanel(new TeamModel()))
        wicketTester.dumpPage()
        wicketTester.debugComponentTrees()
        // input name and validate field
        val field = wicketTester.getComponentFromLastRenderedPage("form:name:input")
        wicketTester.assertNoAjaxValidationError(field, "teamname")
        // set teamname to existing team and revalidate field
        wicketTester.assertAjaxValidationError(field, "Juve Amateure")
    }


}