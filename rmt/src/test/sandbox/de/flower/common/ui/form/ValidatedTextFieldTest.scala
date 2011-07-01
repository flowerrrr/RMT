package de.flower.common.ui.form

import org.slf4j.{LoggerFactory, Logger}
import org.apache.wicket.util.tester.WicketTester
import org.testng.annotations.{BeforeMethod, Test}

/**
 *
 * @author oblume
 */

class ValidatedTextFieldTest {

    private final val log: Logger = LoggerFactory.getLogger(this.getClass)

    private var wicketTester: WicketTester = _

    /**
     * Need to initialize wicket tester before every test, since there are issues with parallel tests.
     */
    @BeforeMethod
    def init() {
        wicketTester = new WicketTester
    }


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
}