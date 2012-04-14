package de.flower.rmt.ui.manager.page.events;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.EventType;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.ui.common.panel.ActivityFeedPanel;
import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.manager.page.event.EventPage;
import de.flower.rmt.ui.model.EventModel;
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
