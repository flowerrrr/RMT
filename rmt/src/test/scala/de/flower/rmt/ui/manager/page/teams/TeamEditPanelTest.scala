package de.flower.rmt.ui.manager.page.teams

import de.flower.rmt.test.WicketTests
import org.testng.annotations.Test
/**
 * 
 * @author oblume
 */

class TeamEditPanelTest extends WicketTests {

    @Test
    def validateUniquenessConstraint() {
        val panel: TeamEditPanel = wicketTester.startPanel(classOf[TeamEditPanel]).asInstanceOf[TeamEditPanel]
        panel.init(null)
        wicketTester.dumpPage()
        wicketTester.debugComponentTrees()
        // input name and validate field
        formTester = wicketTester.newFormTester("form")
        val field = wicketTester.getComponentFromLastRenderedPage("form:name:name")
        assertValidation(field, "teamname", true)
        // set teamname to existing team and revalidate field
        assertValidation(field, "Juve Amateure", false)
    }


}