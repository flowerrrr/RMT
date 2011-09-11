package de.flower.rmt.ui.manager.page.events

import de.flower.rmt.test.WicketTests
import org.testng.annotations.Test
import de.flower.rmt.ui.manager.page.squad.SquadPage
import org.apache.wicket.model.Model
import de.flower.rmt.ui.manager.page.events.EventsPage

/**
 * 
 * @author oblume
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