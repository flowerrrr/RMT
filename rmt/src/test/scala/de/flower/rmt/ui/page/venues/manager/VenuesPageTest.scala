package de.flower.rmt.ui.page.venues.manager

import de.flower.rmt.test.AbstractWicketIntegrationTests
import org.testng.annotations.Test
/**
 * 
 * @author flowerrrr
 */

class VenuesPageTest extends AbstractWicketIntegrationTests {

    @Test
    def testRender() {
        wicketTester.startPage(new VenuesPage())
        wicketTester.dumpPage()
    }


}