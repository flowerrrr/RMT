package de.flower.rmt.ui.manager.page.venues

import de.flower.rmt.test.WicketTests
import org.testng.annotations.Test
import de.flower.rmt.ui.model.VenueModel

/**
 *
 * @author flowerrrr
 */

class VenueEditPanelTest extends WicketTests {

    @Test(enabled = false /* geocoding currently deactivated */)
    def testGeocoding() {
        val panel = new VenueEditPanel(new VenueModel())

        wicketTester.startComponentInPage(panel)
        wicketTester.dumpPage()
        wicketTester.debugComponentTrees()
        // input name and validate field
        val field = wicketTester.getComponentFromLastRenderedPage("form:address")
        wicketTester.newFormTester("form").setValue(field, "Werner-Heisenberg-Allee 25\n 80939 München")
        wicketTester.clickLink("form:geocodeButton")
        wicketTester.dumpPage()
    }


}