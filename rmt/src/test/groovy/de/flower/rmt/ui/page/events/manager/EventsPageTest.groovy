package de.flower.rmt.ui.page.events.manager

import de.flower.rmt.test.AbstractRMTWicketIntegrationTests
import org.testng.annotations.Test


class EventsPageTest extends AbstractRMTWicketIntegrationTests {

    @Test
    def void testRender() {
        wicketTester.startPage(new EventsPage())
        wicketTester.dumpPage()
    }


}