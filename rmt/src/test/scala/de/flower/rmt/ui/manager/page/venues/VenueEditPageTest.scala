package de.flower.rmt.ui.manager.page.venues

import de.flower.rmt.test.AbstractWicketIntegrationTests
import org.testng.annotations.Test
/**
 * 
 * @author flowerrrr
 */

class VenueEditPageTest extends AbstractWicketIntegrationTests {

    @Test
    def testRender() {
        wicketTester.startPage(new VenueEditPage())
        wicketTester.dumpPage()
    }


}