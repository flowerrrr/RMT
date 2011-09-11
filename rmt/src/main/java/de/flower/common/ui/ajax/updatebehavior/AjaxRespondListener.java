package de.flower.common.ui.ajax.updatebehavior;

import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import java.util.List;

/**
 * @author oblume
 */
public class AjaxRespondListener implements AjaxRequestTarget.ITargetRespondListener {

    private AjaxEvent[] events;

    public AjaxRespondListener(AjaxEvent... events) {
        this.events = events;
    }

    @Override
    public void onTargetRespond(final AjaxRequestTarget target) {
        Page page = target.getPage();

        IVisitor<Component, Void> visitor = new IVisitor<Component, Void>() {

            @Override
            public void component(Component component, IVisit<Void> objectIVisit) {
                List<AjaxUpdateBehavior> behaviors = component.getBehaviors(AjaxUpdateBehavior.class);
                if (!behaviors.isEmpty()) {
                    // only one ajax update behavior allowed per component
                    AjaxUpdateBehavior behavior = behaviors.get(0);
                    if (behavior.checkForComponentUpdate(events)) {
                        target.add(component);
                        // in order to force a re-read of all the model data we detach the component and all it's children
                        // thereby detaching all models. this could be fine tuned, cause in some cases detaching is not
                        // necessary and one could save a database lookup.
                        component.detach();
                    }
                }
            }
        };
        page.visitChildren(visitor);
        // and don't forget to visit page itself.
        visitor.component(page, null);
    }
}
