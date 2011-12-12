package de.flower.rmt.ui.player

import de.flower.rmt.test.AbstractWicketIntegrationTests
import org.testng.annotations.Test
import de.flower.rmt.ui.common.page.INavigationPanelAware

/**
 * 
 * @author flowerrrr
 */

class NavigationPanelTest extends AbstractWicketIntegrationTests {

    @Test
    def testRender() {
        wicketTester.startComponentInPage(new NavigationPanel(new INavigationPanelAware {
            def getActiveTopBarItem = "home"
        }))
        wicketTester.dumpPage()
    }


}