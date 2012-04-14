package de.flower.rmt.ui.page.events.manager;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.EventType;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.ui.model.EventModel;
import de.flower.rmt.ui.page.event.manager.EventPage;
import de.flower.rmt.ui.panel.ActivityFeedPanel;
import de.flower.rmt.ui.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class EventsSecondaryPanel extends BasePanel {

    @SpringBean
    private IEventManager eventManager;

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
