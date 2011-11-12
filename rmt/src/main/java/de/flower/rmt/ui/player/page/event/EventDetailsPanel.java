package de.flower.rmt.ui.player.page.event;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.EventType;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

/**
 * @author flowerrrr
 */
public class EventDetailsPanel extends BasePanel {

    public EventDetailsPanel(final IModel<Event> model) {
        super(model);
        Event event = model.getObject();

        add(new Label("team", event.getTeam().getName()));
        add(DateLabel.forDateStyle("date", Model.of(event.getDate()), "S-"));
        add(DateLabel.forDateStyle("time", Model.of(event.getTime().toDateTimeToday().toDate()), "-S"));
        add(new Label("type", new ResourceModel(EventType.from(event).getResourceKey())));
        add(new Label("venue", event.getVenue().getName()));
        add(new Label("summary", event.getSummary()));
        add(new Label("comment", event.getComment()));

    }

 }
