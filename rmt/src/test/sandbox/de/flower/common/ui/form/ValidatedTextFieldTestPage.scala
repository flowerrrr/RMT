package de.flower.common.ui.form

import org.apache.wicket.markup.html.WebPage
import de.flower.common.ui.ajax.updatebehavior.events.Event
import org.apache.wicket.MarkupContainer._
import org.apache.wicket.ajax.markup.html.AjaxLink
import org.apache.wicket.ajax.AjaxRequestTarget
import de.flower.common.ui.ajax.updatebehavior.{AjaxRespondListener, AjaxUpdateBehavior}
import de.flower.common.ui.form.TestTextFieldPanel

/**
 * 
 * @author oblume
 */

class ValidatedTextFieldTestPage extends WebPage {

    val field = new TestTextFieldContainer("foobar")
    add (field)
    val panel = new TestTextFieldPanel("panel")
    add(panel)

    add(new AjaxLink[Void](("link1")) {
        def onClick(target: AjaxRequestTarget): Unit = {
            target.add(field)
            target.add(panel)
        }
    })

}