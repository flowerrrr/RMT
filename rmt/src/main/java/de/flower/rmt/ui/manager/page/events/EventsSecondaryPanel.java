package de.flower.rmt.ui.manager.page.events;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.EventType;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.manager.page.event.EventEditPage;
import de.flower.rmt.ui.model.EventModel;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Arrays;

/**
 * @author flowerrrr
 */
public class EventsSecondaryPanel extends BasePanel {

    @SpringBean
    private IEventManager eventManager;

    public EventsSecondaryPanel() {

        IChoiceRenderer<EventType> renderer = new IChoiceRenderer<EventType>() {

            @Override
            public Object getDisplayValue(final EventType object) {
                return new ResourceModel(object.getResourceKey()).getObject();
            }

            @Override
            public String getIdValue(final EventType object, final int index) {
                return "" + index;
            }
        };
        DropDownChoice<EventType> eventTypeSelect = new DropDownChoice<EventType>("select", new Model(), Arrays.asList(EventType.values()), renderer) {
            @Override
            protected boolean wantOnSelectionChangedNotifications() {
                return true;
            }

            @Override
            protected void onSelectionChanged(final EventType eventType) {
                Event event = eventManager.newInstance(eventType);
                setResponsePage(new EventEditPage(new EventModel(event)));
            }
        };
        add(eventTypeSelect);
    }
}
