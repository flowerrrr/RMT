package de.flower.rmt.ui.common.page

import de.flower.rmt.test.WicketTests
import org.testng.annotations.Test

/**
 * 
 * @author oblume
 */

class HomePageTest extends WicketTests {

    @Test
    def renderPage() {
        wicketTester.startPage(classOf[HomePage])
        wicketTester.dumpPage()
    }

}