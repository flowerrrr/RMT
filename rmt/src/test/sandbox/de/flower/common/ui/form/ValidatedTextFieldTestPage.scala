package de.flower.common.ui.form

import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.ajax.markup.html.AjaxLink
import org.apache.wicket.ajax.AjaxRequestTarget
/**
 * 
 * @author flowerrrr
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