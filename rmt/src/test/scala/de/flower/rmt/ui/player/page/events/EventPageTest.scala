package de.flower.rmt.ui.player.page.events

import de.flower.rmt.test.WicketTests
import org.testng.annotations.Test
import de.flower.rmt.ui.model.EventModel

/**
 * 
 * @author flowerrrr
 */

class EventPageTest extends WicketTests {

    @Test
    def renderPage() {
        val event = testData.createEvent()
        wicketTester.startPage(new EventPage(new EventModel(event)))
        wicketTester.dumpPage()
    }


}