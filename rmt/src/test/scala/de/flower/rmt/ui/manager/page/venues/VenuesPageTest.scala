package de.flower.rmt.ui.manager.page.venues

import de.flower.rmt.test.WicketTests
import org.testng.annotations.Test
/**
 * 
 * @author flowerrrr
 */

class VenuesPageTest extends WicketTests {

    @Test
    def renderPage() {
        wicketTester.startPage(new VenuesPage())
        wicketTester.dumpPage()
    }


}