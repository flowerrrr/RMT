package de.flower.common.ui.ajax.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.event.IEvent;

/**
 * Add to any component that wants to listen to arbitrary ajax requests.
 *
 * @author flowerrrr
 */
public abstract class AjaxRequestListener extends Behavior {

    @Override
    public void onEvent(final Component component, final IEvent<?> event) {
        if (event.getPayload() instanceof AjaxRequestTarget) {
            AjaxRequestTarget target = (AjaxRequestTarget) event.getPayload();
            onAjaxRequest(target);
        }
    }

    protected abstract void onAjaxRequest(AjaxRequestTarget target);
}
