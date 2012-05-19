package de.flower.rmt.ui.page.events.manager

import de.flower.rmt.test.AbstractRMTWicketIntegrationTests
import org.testng.annotations.Test

/**
 * 
 * @author flowerrrr
 */

class EventsPageTest extends AbstractRMTWicketIntegrationTests {

    @Test
    def void testRender() {
        wicketTester.startPage(new EventsPage())
        wicketTester.dumpPage()
    }


}