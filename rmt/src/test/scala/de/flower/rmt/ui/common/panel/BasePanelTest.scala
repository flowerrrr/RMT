package de.flower.rmt.ui.common.panel

import org.testng.annotations.Test
import org.testng.Assert._
import de.flower.rmt.test.WicketTests

/**
 * 
 * @author flowerrrr
 */

class BasePanelTest extends WicketTests {

    @Test
    def testDefaultId() {
        val panel = new TestPanel()
        assertEquals(panel.getId, "testPanel")
    }

}

class TestPanel extends BasePanel {


}