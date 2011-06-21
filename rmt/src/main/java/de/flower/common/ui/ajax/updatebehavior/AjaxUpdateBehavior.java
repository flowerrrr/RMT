package de.flower.common.ui.ajax.updatebehavior;

import de.flower.common.ui.ajax.updatebehavior.events.Event;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;

/**
 * @author oblume
 */
public class AjaxUpdateBehavior extends Behavior {

    private Event registeredEvent;

    public AjaxUpdateBehavior(Component component, Event registeredEvent) {
        this.registeredEvent = registeredEvent;
        component.setOutputMarkupId(true);
    }

    /**
     * Determine if component needs to be updated.
     *
     * @param events
     * @return
     */
    public boolean checkForComponentUpdate(Event[] events) {
        for (Event triggeredEvent : events) {
            if (registeredEvent.matches(triggeredEvent)) {
                return true;
            }
        }
        return false;
    }
}
