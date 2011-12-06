package de.flower.rmt.ui.common.form.field

import de.flower.rmt.test.AbstractWicketTests
import org.apache.wicket.markup.Markup
import org.apache.wicket.markup.html.WebPage
import org.testng.annotations.Test
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.model.CompoundPropertyModel
/**
 * @author flowerrrr
 */
class FormFieldPanelTest extends AbstractWicketTests {

    @Test
    def testRender {
        wicketTester.startPage(new FormFieldPanelTestPage)
        wicketTester.dumpPage()
        wicketTester.assertContains("fooName")
    }
}

class FormFieldPanelTestPage extends WebPage {

    {
        val entity = new TestEntity("fooName", "fooTime")
        val form = new Form[TestEntity]("form", new CompoundPropertyModel[TestEntity](entity))
        add(form)
        form.add(new TextFieldPanel("name"))
        form.add(new DropDownChoicePanel[String]("time", List("a", "b", "c").toList))
    }

    override def getAssociatedMarkup: Markup = {
        return Markup.of("<html><body>\n"
                + "<form wicket:id='form'>\n"
                // can use whatever tag we like, since the tag is rewritten by the panel
                + "<foobar wicket:id='name' labelKey='label.name' />\n"
                + "<rmt:select wicket:id='time' labelKey='label.time' />\n"
                + "</form>\n"
                + "</body></html>")
    }
}

class TestEntity(name: String, time: String) {

    def getName = name
    def getTime = time
}