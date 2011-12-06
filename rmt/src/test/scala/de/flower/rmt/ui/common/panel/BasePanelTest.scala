package de.flower.rmt.ui.common.panel

import org.testng.annotations.Test
import org.testng.Assert._
/**
 * 
 * @author flowerrrr
 */

class BasePanelTest  {

    @Test
    def testDefaultId() {
        val panel = new TestPanel()
        assertEquals(panel.getId, "testPanel")
    }

}

class TestPanel extends BasePanel {


}