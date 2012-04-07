package de.flower.rmt.ui.common.page.event;

import de.flower.rmt.model.Surface;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.EventType;
import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.model.ModelFactory;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

/**
 * @author flowerrrr
 */
public class EventDetailsPanel extends BasePanel<Event> {

    public EventDetailsPanel(final IModel<Event> model) {
        setDefaultModel(new CompoundPropertyModel<Object>(ModelFactory.eventModelWithAllAssociations(model.getObject())));

        add(new Label("team.name"));
        add(DateLabel.forDateStyle("date", "S-"));
        add(DateLabel.forDateStyle("timeAsDate", "-S"));
        add(new Label("type", new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                return new ResourceModel(EventType.from(model.getObject()).getResourceKey()).getObject();
            }
        }));
        add(new Label("opponent.name") {
            @Override
            public boolean isVisible() {
                return EventType.isMatch(model.getObject());
            }
        });
        add(new Label("venue.name"));
        add(new Label("surface", new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                return new ResourceModel(Surface.getResourceKey(model.getObject().getSurface())).getObject();
            }
        }));
        add(new Label("summary"));
        add(new Label("comment"));
    }
}
