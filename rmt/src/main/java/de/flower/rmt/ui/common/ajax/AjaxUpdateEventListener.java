package de.flower.rmt.ui.common.ajax;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author oblume
 */
public class AjaxUpdateEventListener implements IAjaxUpdateEventListener, Serializable {

    private final static Logger log = LoggerFactory.getLogger(AjaxUpdateEventListener.class);

    private Map<AjaxEvent, Set<Component>> map = new HashMap<AjaxEvent, Set<Component>>();

    @Override
    public void register(Component component, AjaxEvent... events) {
        log.debug("Registering [{}] -> [{}].", component, events);
        for (AjaxEvent event : events) {
            Set<Component> components = map.get(event);
            if (components == null) {
                components = new HashSet<Component>();
                map.put(event, components);
            }
            components.add(component);
        }
    }

    @Override
    public void update(AjaxRequestTarget target, AjaxEvent[] events) {
        for (AjaxEvent event : events) {
            Set<Component> components = map.get(event);
            if (components != null) {
                for (Component component : components) {
                    log.debug("Adding [{}] to ajax request target.", component);
                    target.add(component);
                    // in order to force a re-read of all the model data we detach the component and all it's children
                    // thereby detaching all models. this could be fine tuned, cause in some cases detaching is not
                    // necessary and one could save a database lookup.
                    component.detach();
                }
            }
        }
    }
}
