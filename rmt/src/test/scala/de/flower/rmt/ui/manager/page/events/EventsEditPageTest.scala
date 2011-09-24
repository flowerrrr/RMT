package de.flower.rmt.ui.manager.page.events

import de.flower.rmt.test.WicketTests
import org.testng.annotations.Test
import de.flower.rmt.model.event.EventType

/**
 * 
 * @author flowerrrr
 */

class EventsEditPageTest extends WicketTests {

    @Test
    def renderPage() {
        val event = eventManager.newInstance(EventType.Training)
        wicketTester.startPage(new EventsEditPage(event))
        wicketTester.dumpPage()
        wicketTester.debugComponentTrees()
        val formId = "eventEditPanel:form"
        val formTester = getFormTester(formId)
        formTester.select("team:team", 1)
        formTester.setValue("date", "1.12.2011")
        formTester.select("time:time", 4) // select some arbitrary value
        formTester.select("venue:venue", 1)
        formTester.setValue("summary:summary", "New training next sunday")
        formTester.setValue("comment", "Hope you bastards are all coming")
        // save form
        wicketTester.clickLink(formId + ":saveButton")
        wicketTester.dumpPage()
        wicketTester.assertContainsNot("Fehler bei")
   }


}