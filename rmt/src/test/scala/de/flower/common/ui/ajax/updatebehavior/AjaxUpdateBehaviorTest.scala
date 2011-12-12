package de.flower.common.ui.ajax.updatebehavior

import org.testng.Assert._
import org.testng.annotations.Test
import de.flower.common.test.wicket.AbstractWicketUnitTests

/**
 * @author flowerrrr
 */

class AjaxUpdateBehaviorTest extends AbstractWicketUnitTests {

    @Test
    def testRender() {
        wicketTester.startPage(new AjaxUpdateBehaviorTestPage)
        AbstractWicketUnitTests.log.info(wicketTester.getLastResponse.getDocument)
        wicketTester.debugComponentTrees
        wicketTester.clickLink("link1")
        var document: String = wicketTester.getLastResponse.getDocument
        AbstractWicketUnitTests.log.info(document)
        assertTrue(document.contains(">bar<"))
    }

}