package de.flower.rmt.ui.page.event.manager.edit;

import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.event.Event;
import org.apache.wicket.model.IModel;


public class EventEditSecondaryPanel extends BasePanel<Event> {

    public EventEditSecondaryPanel(IModel<Event> model) {
        super(model);

        // add(new AjaxEventListener(Event.class));   does not work with setRenderBodyOnly(true).

        // treat subpanels as top level secondary panels to have spacer between them
        setRenderBodyOnly(true);
        add(new CancelEventPanel(model));
        add(new CopyEventPanel(model));
    }
}
