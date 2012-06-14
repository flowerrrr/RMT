package de.flower.rmt.ui.page.event;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.event.Event;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class EventCanceledPanel extends BasePanel<Event> {

    public EventCanceledPanel(final IModel<Event> model) {
        super(model);
        setOutputMarkupPlaceholderTag(true);
        add(new AjaxEventListener(Event.class));
    }

    @Override
    public boolean isVisible() {
        return getModelObject().isCanceled();
    }

    @Override
    protected String getPanelMarkup() {
        return "<h4><wicket:message key='event.canceled.heading'/></h4><wicket:message key='event.canceled.text'/>";
    }
}
