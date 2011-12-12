package de.flower.rmt.ui.manager.page.opponents

import de.flower.rmt.test.AbstractWicketIntegrationTests
import org.testng.annotations.Test
/**
 * 
 * @author flowerrrr
 */

class OpponentsPageTest extends AbstractWicketIntegrationTests {

    @Test
    def testRender() {
        wicketTester.startPage(new OpponentsPage())
        wicketTester.dumpPage()
    }


}