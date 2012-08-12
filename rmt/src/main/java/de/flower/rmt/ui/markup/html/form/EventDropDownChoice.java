package de.flower.rmt.ui.markup.html.form;

import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.ui.markup.html.form.renderer.EventRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;

/**
 * @author flowerrrr
 */
public class EventDropDownChoice extends DropDownChoice<Event> {

    public EventDropDownChoice(String id) {
        super(id);
        setChoiceRenderer(new EventRenderer(true));
        setNullValid(true);
    }

    @Override
    protected String getNullValidKey() {
        return "event.nullValid";
    }
}
