package de.flower.rmt.ui.page.events.manager;

import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.type.EventType;
import de.flower.rmt.service.EventManager;
import de.flower.rmt.ui.model.EventModel;
import de.flower.rmt.ui.page.event.manager.EventPage;
import de.flower.rmt.ui.panel.activityfeed.ActivityFeedPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.spring.injection.annot.SpringBean;


public class EventsSecondaryPanel extends BasePanel {

    @SpringBean
    private EventManager eventManager;

    public EventsSecondaryPanel() {
        // treat subpanels as top level secondary panels to have spacer between them
        setRenderBodyOnly(true);

        add(new EventTypeSelectPanel() {
            @Override
            public void onSelect(final EventType eventType, final AjaxRequestTarget target) {
                Event event = eventManager.newInstance(eventType);
                setResponsePage(new EventPage(new EventModel(event)));
            }
        });

        add(new ActivityFeedPanel());

     }
}
