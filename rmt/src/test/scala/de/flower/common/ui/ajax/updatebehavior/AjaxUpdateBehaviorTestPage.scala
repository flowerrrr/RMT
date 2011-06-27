package de.flower.common.ui.ajax.updatebehavior

import de.flower.common.ui.ajax.updatebehavior.events.Event
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.markup.html.AjaxLink
import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.model.PropertyModel
import reflect.BeanProperty

/**
 * test page with some ajax updatable components in it.
 * @author oblume
 */

class AjaxUpdateBehaviorTestPage extends WebPage {

    var entity: Entity = _

    entity = new Entity("foo")
    var label: Label = new Label("label", new PropertyModel[Entity](entity, "label"))
    add(label)
    label.add(new AjaxUpdateBehavior(Event.EntityAll(classOf[Entity])))
    add(new AjaxLink[Void](("link1")) {
        def onClick(target: AjaxRequestTarget): Unit = {
            entity.setLabel("bar")
            target.registerRespondListener(new AjaxRespondListener(Event.EntityUpdated(classOf[Entity])))
        }
    })

}

class Entity(@BeanProperty var label: String) {}