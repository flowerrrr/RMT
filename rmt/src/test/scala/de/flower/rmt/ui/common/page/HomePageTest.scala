package de.flower.rmt.ui.common.page

import de.flower.rmt.test.AbstractWicketTests
import org.testng.annotations.Test

/**
 * 
 * @author flowerrrr
 */

class HomePageTest extends AbstractWicketTests {

    @Test
    def testRender() {
        wicketTester.startPage(classOf[HomePage])
        wicketTester.dumpPage()
    }

}