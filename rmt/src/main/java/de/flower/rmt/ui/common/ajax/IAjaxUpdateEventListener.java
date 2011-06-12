package de.flower.rmt.ui.common.ajax;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * @author oblume
 */
public interface IAjaxUpdateEventListener {

    void register(Component component, AjaxEvent... events);

    void update(AjaxRequestTarget target, AjaxEvent[] events);
}
