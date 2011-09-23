package de.flower.rmt.ui.manager.page.events

import de.flower.rmt.test.WicketTests
import org.testng.annotations.Test
/**
 * 
 * @author oblume
 */

class EventsEditPageTest extends WicketTests {

    @Test
    def renderPage() {
        wicketTester.startPage(new EventsEditPage(null))
        wicketTester.dumpPage()
        wicketTester.debugComponentTrees()
        wicketTester.clickLink("eventEditPanel:selectEventType:eventTypes:1:typeLink") // select Training
        wicketTester.dumpPage()
        wicketTester.debugComponentTrees()
        val formId = "eventEditPanel:form"
        val formTester = getFormTester(formId).setValue("date", "1.12.2011")
        formTester.select("time", 4) // select some arbitrary value
        formTester.select("venue", 1)
        formTester.setValue("summary:summary", "New training next sunday")
        formTester.setValue("comment", "Hope you bastards are all coming")
        // save form
        wicketTester.clickLink(formId + ":saveButton")
        wicketTester.dumpPage()


    }


}