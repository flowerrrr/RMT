package de.flower.rmt.ui.manager.page.events;

import de.flower.common.ui.ajax.MyAjaxLink;
import de.flower.rmt.model.event.Event;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.ListModel;

/**
 * Panel to let user select event type before event form is displayed.
 *
 * @author oblume
 */
public abstract class PreCreateEventEditPanel extends Panel {

    public PreCreateEventEditPanel(String id) {
        super(id);

        add(new ListView<Class<? extends Event>>("eventTypes", getEventTypeListModel()) {

            @Override
            protected void populateItem(ListItem<Class<? extends Event>> item) {
                final Class<? extends Event> clazz = item.getModelObject();
                item.add(new MyAjaxLink("typeLink") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        onSelect(clazz, target);
                    }
                }.setBody(new ResourceModel(Event.getTypeResourceKey(clazz))));
            }
        });

    }

    public abstract void onSelect(Class<? extends Event> eventType, AjaxRequestTarget target);

    private ListModel<Class<? extends Event>> getEventTypeListModel() {
        return new ListModel<Class<? extends Event>>(Event.getEventTypes());
    }
}
