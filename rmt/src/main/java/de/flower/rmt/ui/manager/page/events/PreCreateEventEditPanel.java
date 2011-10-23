package de.flower.rmt.ui.manager.page.events;

import de.flower.common.ui.ajax.MyAjaxLink;
import de.flower.rmt.model.event.EventType;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.ListModel;

import java.util.Arrays;

/**
 * Panel to let user select event type before event form is displayed.
 *
 * @author flowerrrr
 */
public abstract class PreCreateEventEditPanel extends BasePanel {

    public PreCreateEventEditPanel(String id) {
        super(id);

        add(new ListView<EventType>("eventTypes", getEventTypeListModel()) {

            @Override
            protected void populateItem(ListItem<EventType> item) {
                final EventType type = item.getModelObject();
                item.add(new MyAjaxLink("typeLink") {
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
