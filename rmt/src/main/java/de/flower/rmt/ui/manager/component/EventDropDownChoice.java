package de.flower.rmt.ui.manager.component;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.ui.common.renderer.EventRenderer;
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
