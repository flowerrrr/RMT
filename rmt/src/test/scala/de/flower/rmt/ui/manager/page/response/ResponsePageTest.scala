package de.flower.rmt.ui.manager.page.response

import de.flower.rmt.test.WicketTests
import org.testng.annotations.Test

/**
 * 
 * @author flowerrrr
 */

class ResponsePageTest extends WicketTests {

    @Test
    def renderPage() {
        val event = testData.createEventWithResponses()
        wicketTester.startPage(new ResponsePage(event.getId()))
        wicketTester.dumpPage()
    }
}