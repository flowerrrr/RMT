package de.flower.rmt.ui.common.renderer;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.EventType;
import de.flower.rmt.util.Dates;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.ResourceModel;

/**
 * @author flowerrrr
 */
public class EventRenderer implements IChoiceRenderer<Event> {

    @Override
    public Object getDisplayValue(final Event event) {
        return getTypeDateSummary(event);
    }

    @Override
    public String getIdValue(final Event object, final int index) {
        if (object.getId() != null) {
            return "" + object.getId();
        } else {
            return "index-" + index;
        }
    }

    public static String getTypeDateSummary(final Event event) {
        String eventType = new ResourceModel(EventType.from(event).getResourceKey()).getObject();
        String date = Dates.formatDateTimeShort(event.getDateTimeAsDate());
        return eventType + " - " + date + " - " + event.getSummary();
    }
}
