package de.flower.rmt.ui.manager.page.events

import de.flower.rmt.test.WicketTests
import org.testng.annotations.Test
/**
 * 
 * @author flowerrrr
 */

class EventsPageTest extends WicketTests {

    @Test
    def renderPage() {
        wicketTester.startPage(new EventsPage())
        wicketTester.dumpPage()
        wicketTester.clickLink("newButton")
        wicketTester.dumpPage()
    }


}