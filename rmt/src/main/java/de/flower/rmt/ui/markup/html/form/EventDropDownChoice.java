package de.flower.rmt.ui.markup.html.form;

import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.ui.markup.html.form.renderer.EventRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * @author flowerrrr
 */
public class EventDropDownChoice extends DropDownChoice<Event> {

    public EventDropDownChoice(String id, IModel<Event> model, IModel<List<Event>> listModel) {
        super(id, model, listModel);
        setChoiceRenderer(new EventRenderer());
    }
}
