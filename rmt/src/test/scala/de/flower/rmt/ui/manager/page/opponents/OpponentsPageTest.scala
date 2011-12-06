package de.flower.rmt.ui.manager.page.opponents

import de.flower.rmt.test.AbstractWicketTests
import org.testng.annotations.Test
/**
 * 
 * @author flowerrrr
 */

class OpponentsPageTest extends AbstractWicketTests {

    @Test
    def testRender() {
        wicketTester.startPage(new OpponentsPage())
        wicketTester.dumpPage()
    }


}