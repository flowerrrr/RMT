package de.flower.common.ui.ajax.updatebehavior

import org.apache.wicket.util.tester.WicketTester
import org.testng.annotations.Test
import org.testng.Assert._
import org.slf4j.{LoggerFactory, Logger}

/**
 * @author oblume
 */

class AjaxUpdateBehaviorTest {

    private final val log: Logger = LoggerFactory.getLogger(this.getClass)

    @Test def testFooBar: Unit = {
        wicketTester.startPage(new AjaxUpdateBehaviorTestPage)
        log.info(wicketTester.getLastResponse.getDocument)
        wicketTester.debugComponentTrees
        wicketTester.clickLink("link1")
        var document: String = wicketTester.getLastResponse.getDocument
        log.info(document)
        assertTrue(document.contains(">bar<"))
    }

    private var wicketTester: WicketTester = new WicketTester
}