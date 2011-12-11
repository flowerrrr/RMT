package de.flower.rmt.ui.manager.page.events;

import de.flower.common.ui.ajax.AjaxLink;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.EventType;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.manager.page.event.EventEditPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class EventsSecondaryPanel extends BasePanel {

    @SpringBean
    private IEventManager eventManager;

    public EventsSecondaryPanel() {
       // fade in panel to preselect event-type before opening the event edit page.
        final EventTypeSelectPanel eventTypeSelectPanel = new EventTypeSelectPanel() {
            @Override
            public void onSelect(EventType eventType, AjaxRequestTarget target) {
                Event event = eventManager.newInstance(eventType);
                setResponsePage(new EventEditPage(event));
            }
        };
        eventTypeSelectPanel.setOutputMarkupPlaceholderTag(true);
        eventTypeSelectPanel.setVisible(false);
        eventTypeSelectPanel.setOutputMarkupId(true);
        add(eventTypeSelectPanel);

        final org.apache.wicket.ajax.markup.html.AjaxLink newButton = new AjaxLink("newButton") {
           @Override
            public void onClick(AjaxRequestTarget target) {
               eventTypeSelectPanel.setVisible(true);
               target.add(eventTypeSelectPanel);
           }
        };
        add(newButton);

     }
}
