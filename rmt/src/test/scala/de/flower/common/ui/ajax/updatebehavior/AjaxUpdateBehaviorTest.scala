package de.flower.common.ui.ajax.updatebehavior

import org.apache.wicket.util.tester.WicketTester
import org.testng.Assert._
import org.slf4j.{LoggerFactory, Logger}
import org.testng.annotations.{BeforeMethod, Test}

/**
 * @author oblume
 */

class AjaxUpdateBehaviorTest {

    private final val log: Logger = LoggerFactory.getLogger(this.getClass)

    private var wicketTester: WicketTester = _

    @BeforeMethod
    def init() {
        wicketTester = new WicketTester
    }

    @Test def testFooBar: Unit = {
        var wicketTester = new WicketTester
        wicketTester.startPage(new AjaxUpdateBehaviorTestPage)
        log.info(wicketTester.getLastResponse.getDocument)
        wicketTester.debugComponentTrees
        wicketTester.clickLink("link1")
        var document: String = wicketTester.getLastResponse.getDocument
        log.info(document)
        assertTrue(document.contains(">bar<"))
    }

}