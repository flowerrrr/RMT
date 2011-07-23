package de.flower.rmt.test

import de.flower.test.AbstractWicketTests
import org.springframework.context.ApplicationContext
import org.apache.wicket.protocol.http.WebApplication
import de.flower.rmt.ui.app.TestApplication
import org.apache.wicket.Component
import de.flower.common.ui.Css

/**
 * 
 * @author oblume
 */

class WicketTests extends AbstractWicketTests {


    override protected def createWebApp(ctx: ApplicationContext, flag: Boolean): WebApplication = {
        return new TestApplication(ctx);
    }

    def assertValidation(field: Component, value: String, assertion: Boolean) {
        formTester.setValue(field, value)
        wicketTester.executeAjaxEvent(field, "onblur")
        wicketTester.dumpPage()
        val cssClass = if (assertion)  Css.VALID else Css.ERROR
        wicketTester.assertContains("class=\"" + cssClass)
    }


}