package de.flower.common.ui.ajax.updatebehavior;

import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author flowerrrr
 */
public class AjaxUpdateBehavior extends Behavior {

    private final static Logger log = LoggerFactory.getLogger(AjaxUpdateBehavior.class);

    private AjaxEvent registeredEvent;

    public AjaxUpdateBehavior(AjaxEvent registeredEvent) {
        this.registeredEvent = registeredEvent;
    }

    /**
     * Called immediately after attaching the behavior. Helps to make the constructor leaner.
     *
     * @param component
     */
    @Override
    public void bind(Component component) {
        component.setOutputMarkupId(true);
    }

    public void onEvent(final AjaxRequestTarget target, final Component component, final AjaxEvent[] events) {

        if (checkForComponentUpdate(events)) {
            if (!component.isVisible()) {
                log.warn("Trying to add invisibile component [{}] to ajax-response", component.getId());
                return;
            }
            addTarget(target, component);
        }
    }

    /**
     * Subclass can override this method when they want provide individual behavior (not just adding the
     * component to the target).
     *
     * @param target
     * @param component
     */
    public void addTarget(final AjaxRequestTarget target, final Component component) {
        target.add(component);
        // in order to force a re-read of all the model data we detach the component and all it's children
        // thereby detaching all models. this could be fine tuned, cause in some cases detaching is not
        // necessary and one could save a database lookup.
        component.detach();
    }

    /**
     * Determine if component needs to be updated.
     *
     * @param events
     * @return
     */
    private boolean checkForComponentUpdate(AjaxEvent[] events) {
        for (AjaxEvent triggeredEvent : events) {
            if (registeredEvent.matches(triggeredEvent)) {
                return true;
            }
        }
        return false;
    }
}
