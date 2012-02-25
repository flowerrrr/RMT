package de.flower.rmt.ui.common.page.event;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.EventType;
import de.flower.rmt.model.event.Event_;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.model.EventModel;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class EventDetailsPanel extends BasePanel<Event> {

    @SpringBean
    private IEventManager eventManager;

    public EventDetailsPanel(final IModel<Event> model) {
        setDefaultModel(new CompoundPropertyModel<Object>(getEventModel(model)));

        add(new Label("team.name"));
        add(DateLabel.forDateStyle("date", "S-"));
        add(DateLabel.forDateStyle("timeAsDate", "-S"));
        add(new Label("type", new ResourceModel(EventType.from(model.getObject()).getResourceKey())));
        add(new Label("venue.name"));
        add(new Label("summary"));
        add(new Label("comment"));
    }

    /**
     * Return event instance initialized with team and venue association.
     */
    IModel<Event> getEventModel(final IModel<Event> model) {
        return new EventModel(model.getObject().getId(), Event_.team, Event_.venue);
    }

 }
