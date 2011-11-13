package de.flower.rmt.ui.manager.page.venues

import de.flower.rmt.test.WicketTests
import org.testng.annotations.Test
/**
 * 
 * @author flowerrrr
 */

class VenueEditPageTest extends WicketTests {

    @Test
    def renderPage() {
        wicketTester.startPage(new VenueEditPage())
        wicketTester.dumpPage()
    }


}