package de.flower.common.ui.ajax.updatebehavior;

import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;

/**
 * @author oblume
 */
public class AjaxUpdateBehavior extends Behavior {

    private AjaxEvent registeredEvent;

    public AjaxUpdateBehavior(AjaxEvent registeredEvent) {
        this.registeredEvent = registeredEvent;
    }

    /**
     * Called immediately after attaching the behavior. Helps to make the constructor leaner.
     * @param component
     */
    @Override
    public void bind(Component component) {
        component.setOutputMarkupId(true);
    }

    /**
     * Determine if component needs to be updated.
     *
     * @param events
     * @return
     */
    public boolean checkForComponentUpdate(AjaxEvent[] events) {
        for (AjaxEvent triggeredEvent : events) {
            if (registeredEvent.matches(triggeredEvent)) {
                return true;
            }
        }
        return false;
    }
}
