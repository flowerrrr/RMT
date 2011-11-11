package de.flower.rmt.ui.player

import de.flower.rmt.test.WicketTests
import org.testng.annotations.Test
import de.flower.rmt.ui.model.EventModel
import page.events.EventPage
import de.flower.rmt.ui.player.NavigationPanel
import de.flower.rmt.ui.common.page.INavigationPanelAware

/**
 * 
 * @author flowerrrr
 */

class NavigationPanelTest extends WicketTests {

    @Test
    def renderPanel() {
        wicketTester.startComponentInPage(new NavigationPanel(new INavigationPanelAware {
            def getActiveTopBarItem = "home"
        }))
        wicketTester.dumpPage()
    }


}