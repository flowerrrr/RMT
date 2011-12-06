package de.flower.rmt.ui.manager.page.events

import de.flower.rmt.test.AbstractWicketTests
import org.testng.annotations.Test
/**
 * 
 * @author flowerrrr
 */

class EventsPageTest extends AbstractWicketTests {

    @Test
    def testRender() {
        wicketTester.startPage(new EventsPage())
        wicketTester.dumpPage()
        wicketTester.clickLink("newButton")
        wicketTester.dumpPage()
    }


}