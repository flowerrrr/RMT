package de.flower.rmt.ui.manager.page.venues

import de.flower.rmt.test.WicketTests
import org.testng.annotations.Test
/**
 * 
 * @author oblume
 */

class VenueEditPanelTest extends WicketTests {

    @Test
    def testPanel() {
        val panel: VenueEditPanel = wicketTester.startPanel(classOf[VenueEditPanel]).asInstanceOf[VenueEditPanel]
/*
        panel.init(null)
        wicketTester.dumpPage()
        wicketTester.debugComponentTrees()
        // input name and validate field
        formTester = wicketTester.newFormTester("form")
        val field = wicketTester.getComponentFromLastRenderedPage("form:name:name")
        assertValidation(field, "teamname", true)
        // set teamname to existing team and revalidate field
        assertValidation(field, "Juve Amateure", false)
*/
    }


}