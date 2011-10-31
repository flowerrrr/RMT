package de.flower.rmt.ui.common.form

import de.flower.rmt.test.WicketTests
import org.apache.wicket.markup.Markup
import org.apache.wicket.markup.html.WebPage
import org.testng.annotations.Test
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.model.CompoundPropertyModel
import scala.collection.JavaConversions._
import java.util.Arrays

/**
 * @author flowerrrr
 */
class FormFieldPanelTest extends WicketTests {

    @Test
    def testPanelRenders {
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
                // can use whatever tag we like, since only the body is rendered
                + "<wicket:container wicket:id='name' labelKey='label.name' />\n"
                + "<rmt:select wicket:id='time' labelKey='label.time' />\n"
                + "</form>\n"
                + "</body></html>")
    }
}

class TestEntity(name: String, time: String) {

    def getName = name
    def getTime = time
}