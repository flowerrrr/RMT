package de.flower.common.ui.ajax.updatebehavior

import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.markup.html.AjaxLink
import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.markup.html.basic.Label
import reflect.BeanProperty
import de.flower.common.ui.tooltips.TooltipBehavior
import org.apache.wicket.model.{Model, PropertyModel}

/**
 * test page with some ajax updatable components in it.
 * @author flowerrrr
 */

class AjaxUpdateBehaviorTestPage extends WebPage {

    var entity: Entity = _

    entity = new Entity("foo")
    var label: Label = new Label("label", new PropertyModel[Entity](entity, "label"))
    add(label)
    label.add(new AjaxUpdateBehavior(AjaxEvent.EntityAll(classOf[Entity])))
    val link = new AjaxLink[Void](("link1")) {
        def onClick(target: AjaxRequestTarget): Unit = {
            entity.setLabel("bar")
            target.registerRespondListener(new AjaxRespondListener(AjaxEvent.EntityUpdated(classOf[Entity])))
        }
    };
    link.add(new TooltipBehavior(Model.of("hello")));
    add(link);
}

class Entity(@BeanProperty var label: String) {}