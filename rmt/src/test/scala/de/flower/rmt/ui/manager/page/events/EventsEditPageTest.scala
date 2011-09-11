package de.flower.rmt.ui.manager.page.events

import de.flower.rmt.test.WicketTests
import org.testng.annotations.Test
/**
 * 
 * @author oblume
 */

class EventsEditPageTest extends WicketTests {

    @Test
    def renderPage() {
        wicketTester.startPage(new EventsEditPage(null))
        wicketTester.dumpPage()
        wicketTester.debugComponentTrees()
        wicketTester.clickLink("eventEditPanel:selectEventType:eventTypes:1:typeLink") // select Training
        wicketTester.dumpPage()
    }


}