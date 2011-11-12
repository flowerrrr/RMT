package de.flower.rmt.ui.common.form

import de.flower.rmt.test.WicketTests
import org.apache.wicket.markup.Markup
import org.testng.annotations.Test
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.model.CompoundPropertyModel
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.markup.html.panel.FeedbackPanel
import org.apache.wicket.markup.html.{WebMarkupContainer, WebPage}
import de.flower.common.ui.ajax.MyAjaxSubmitLink
import de.flower.rmt.model.event.{Training, Event}

/**
 * @author flowerrrr
 */
class EntityFormTest extends WicketTests {

    @Test
    def testPanelRenders {
        wicketTester.startPage(new FormFieldPanelTestPage)
        wicketTester.dumpPage()
        wicketTester.debugComponentTrees()
        wicketTester.assertComponent("hasErrors", classOf[WebMarkupContainer])
        wicketTester.assertComponent("feedback", classOf[FeedbackPanel])
        wicketTester.assertComponent("saveButton", classOf[MyAjaxSubmitLink])
    }
}

class EntityFormTestPage extends WebPage {

    {
        val entity = new Training()
        val form = new EntityForm[Event]("form", new CompoundPropertyModel[Event](entity)) {
            protected def onSubmit(target: AjaxRequestTarget, form: Form[Event]) {}
        }
        add(form)
        form.add(new TextFieldPanel("name"))
    }

    override def getAssociatedMarkup: Markup = {
        return Markup.of("<html><body>\n"
                + "<form wicket:id='form'>\n"
                // can use whatever tag we like, since only the body is rendered
                + "<wicket:container wicket:id='name' labelKey='label.name' />\n"
                + "</form>\n"
                + "</body></html>")
    }
}

