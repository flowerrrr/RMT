package de.flower.rmt.ui.manager.page.venues

import de.flower.rmt.test.WicketTests
import org.testng.annotations.Test
/**
 *
 * @author flowerrrr
 */

class VenueEditPanelTest extends WicketTests {

    @Test
    def testGeocoding() {
        val panel = new VenueEditPanel("foobar")
        panel.init(null)

        wicketTester.startComponentInPage(panel)
        wicketTester.dumpPage()
        wicketTester.debugComponentTrees()
        // input name and validate field
        val field = wicketTester.getComponentFromLastRenderedPage("form:address")
        getFormTester().setValue(field, "Werner-Heisenberg-Allee 25\n 80939 München")
        wicketTester.clickLink("form:geocodeButton")
        wicketTester.dumpPage()
    }


}