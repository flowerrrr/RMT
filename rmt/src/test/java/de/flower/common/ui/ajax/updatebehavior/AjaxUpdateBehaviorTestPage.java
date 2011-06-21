package de.flower.common.ui.ajax.updatebehavior;

import de.flower.common.ui.ajax.updatebehavior.events.Event;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;

/**
 * test page with some ajax updatable components in it.
 * @author oblume
 */
public class AjaxUpdateBehaviorTestPage extends WebPage {

    private Entity entity = new Entity();

    public AjaxUpdateBehaviorTestPage() {

        entity.setLabel1("foo");
        Label label;
        add(label = new Label("label1", new PropertyModel<Object>(entity, "label1")));
        label.add(new AjaxUpdateBehavior(label, Event.EntityAll(Entity.class)));

        add(new AjaxLink<Void>("link1") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                entity.setLabel1("bar");
                target.registerRespondListener(new AjaxRespondListener(Event.EntityUpdated(Entity.class)));
            }

        }) ;


    }

    public static class Entity {

        private String label1;

        public String getLabel1() {
            return label1;
        }

        public void setLabel1(String label1) {
            this.label1 = label1;
        }
    }
}
