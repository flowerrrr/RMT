package de.flower.common.ui.form

import org.slf4j.{LoggerFactory, Logger}
import org.testng.annotations.Test
import org.apache.wicket.util.tester.WicketTester

/**
 * 
 * @author oblume
 */

class ValidatedTextFieldTest {

    private final val log: Logger = LoggerFactory.getLogger(this.getClass)

    @Test def testFooBar: Unit = {
        wicketTester.getApplication.getMarkupSettings.setStripWicketTags(true)
        wicketTester.startPage(new ValidatedTextFieldTestPage)
        log.info(wicketTester.getLastResponse.getDocument)
        wicketTester.debugComponentTrees
        // test ajax update
        wicketTester.clickLink("link1")
        var document: String = wicketTester.getLastResponse.getDocument
        log.info(document)

    }

    private var wicketTester: WicketTester = new WicketTester

}