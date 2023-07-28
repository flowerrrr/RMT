package de.flower.rmt.ui.page.teams.manager

import de.flower.rmt.test.AbstractRMTWicketIntegrationTests
import de.flower.rmt.ui.model.TeamModel
import org.testng.annotations.Test


class TeamEditPanelTest extends AbstractRMTWicketIntegrationTests {

    @Test
    def void testRender() {
        wicketTester.startComponentInPage(new TeamEditPanel(new TeamModel()))
        wicketTester.dumpComponentWithPage()
    }

    @Test
    def void validateUniquenessConstraint() {
        wicketTester.startComponentInPage(new TeamEditPanel(new TeamModel()))
        wicketTester.dumpPage()
        wicketTester.debugComponentTrees()
        // input name and validate field
        def field = wicketTester.getComponentFromLastRenderedPage("form:name:input")
        wicketTester.assertNoAjaxValidationError(field, "teamname")
        // set teamname to existing team and revalidate field
        wicketTester.assertAjaxValidationError(field, "Juve Amateure")
    }
}