package de.flower.rmt.ui.manager

import de.flower.rmt.test.WicketTests
import org.testng.annotations.Test
/**
 * 
 * @author flowerrrr
 */

class ManagerHomePageTest extends WicketTests {

    @Test
    def renderPage() {
        wicketTester.startPage(new ManagerHomePage())
        wicketTester.dumpPage()
    }

}