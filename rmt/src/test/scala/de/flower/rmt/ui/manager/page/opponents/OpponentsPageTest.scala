package de.flower.rmt.ui.manager.page.opponents

import de.flower.rmt.test.WicketTests
import org.testng.annotations.Test
/**
 * 
 * @author flowerrrr
 */

class OpponentsPageTest extends WicketTests {

    @Test
    def renderPage() {
        wicketTester.startPage(new OpponentsPage())
        wicketTester.dumpPage()
    }


}