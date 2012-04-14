package de.flower.rmt.ui.page.events.manager

import de.flower.rmt.test.AbstractWicketIntegrationTests
import org.testng.annotations.Test
/**
 * 
 * @author flowerrrr
 */

class EventsPageTest extends AbstractWicketIntegrationTests {

    @Test
    def testRender() {
        wicketTester.startPage(new EventsPage())
        wicketTester.dumpPage()
    }


}