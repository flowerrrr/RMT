package de.flower.rmt.ui.page.teams.manager

import de.flower.rmt.test.AbstractWicketIntegrationTests
import org.testng.annotations.Test
import de.flower.rmt.ui.model.TeamModel

/**
 * 
 * @author flowerrrr
 */

class TeamEditPanelTest extends AbstractWicketIntegrationTests {


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