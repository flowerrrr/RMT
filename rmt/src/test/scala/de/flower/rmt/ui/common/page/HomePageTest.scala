package de.flower.rmt.ui.common.page

import de.flower.rmt.test.AbstractWicketIntegrationTests
import org.testng.annotations.Test

/**
 * 
 * @author flowerrrr
 */

class HomePageTest extends AbstractWicketIntegrationTests {

    @Test
    def testRender() {
        wicketTester.startPage(classOf[HomePage])
        wicketTester.dumpPage()
    }

}