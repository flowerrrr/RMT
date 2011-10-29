package de.flower.rmt.ui.common.form

import de.flower.rmt.test.WicketTests
import org.apache.wicket.markup.Markup
import org.apache.wicket.markup.html.WebPage
import org.testng.annotations.Test
import org.apache.wicket.markup.html.form.Form
import de.flower.common.validation.TestEntity
import org.apache.wicket.model.CompoundPropertyModel

/**
 * @author flowerrrr
 */
object InputFieldPanelTest {

    private class InputFieldPanelTestPage extends WebPage {

        val entity = new TestEntity("fooName")
        val form = new Form[TestEntity]("form", new CompoundPropertyModel[TestEntity](entity))
        add(form)
        form.add(new InputFieldPanel("name"))

        override def getAssociatedMarkup: Markup = {
            return Markup.of("<html><body>\n"
                    + "<form wicket:id='form'>\n"
                    + "<rmt:input wicket:id='name' labelKey='hello.world' />"
                    + "</form>"
                    + "\n</body></html>")
        }
    }

}

class InputFieldPanelTest extends WicketTests {

    @Test def testPanelRenders: Unit = {
        wicketTester.startPage(new InputFieldPanelTest.InputFieldPanelTestPage)
        wicketTester.dumpPage()
        wicketTester.assertContains("fooName")
    }
}

class TestEntity(name: String) {

    def getName = name
}