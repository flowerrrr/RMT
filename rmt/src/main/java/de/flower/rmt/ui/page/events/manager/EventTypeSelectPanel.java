package de.flower.rmt.ui.page.events.manager;

import de.flower.common.ui.ajax.markup.html.AjaxLink;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.type.EventType;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.ListModel;

import java.util.Arrays;

/**
 * Panel to let user select event type before event form is displayed.
 */
public abstract class EventTypeSelectPanel extends BasePanel {

    public EventTypeSelectPanel() {

        add(new ListView<EventType>("eventTypes", getEventTypeListModel()) {

            @Override
            protected void populateItem(ListItem<EventType> item) {
                final EventType type = item.getModelObject();
                item.add(new AjaxLink("typeLink") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        onSelect(type, target);
                    }
                }.setBody(new ResourceModel(type.getResourceKey())));
            }
        });

    }

    public abstract void onSelect(EventType eventType, AjaxRequestTarget target);

    private ListModel<EventType> getEventTypeListModel() {
        return new ListModel<EventType>(Arrays.asList(EventType.values()));
    }
}
