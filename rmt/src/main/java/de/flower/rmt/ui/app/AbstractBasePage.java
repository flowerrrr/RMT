package de.flower.rmt.ui.app;

import de.flower.common.util.Misc;
import de.flower.rmt.ui.common.ajax.AjaxEvent;
import de.flower.rmt.ui.common.ajax.AjaxUpdateEventListener;
import de.flower.rmt.ui.common.ajax.IAjaxUpdateEventListener;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author oblume
 */
public class AbstractBasePage extends WebPage implements IAjaxUpdateEventListener {

    protected static final Logger log = LoggerFactory.getLogger(Misc.getClassStatic());

    private IAjaxUpdateEventListener ajaxUpdateEventListener = new AjaxUpdateEventListener();

    public void register(Component component, AjaxEvent... events) {
        component.setOutputMarkupId(true);
        ajaxUpdateEventListener.register(component, events);
    }

    @Override
    public void update(AjaxRequestTarget target, AjaxEvent[] events) {
        ajaxUpdateEventListener.update(target, events);
    }

}
