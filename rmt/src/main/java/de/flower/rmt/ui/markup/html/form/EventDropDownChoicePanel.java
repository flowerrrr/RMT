package de.flower.rmt.ui.markup.html.form;

import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.entity.event.QEvent;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.ui.markup.html.form.field.AbstractFormFieldPanel;
import de.flower.rmt.ui.markup.html.form.field.DropDownChoicePanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class EventDropDownChoicePanel extends DropDownChoicePanel<Event> {

    @SpringBean
    private IEventManager eventManager;

    public EventDropDownChoicePanel(String id) {
        super(id, new EventDropDownChoice(AbstractFormFieldPanel.ID));
        setChoices(getEventChoices());
    }

    private IModel<List<Event>> getEventChoices() {
        return new LoadableDetachableModel<List<Event>>() {
            @Override
            protected List<Event> load() {
                // return eventManager.findAll(QEvent.event.team, QMatch.match.opponent);
                List<Event> list = eventManager.findAll(QEvent.event.team, QEvent.event.opponent);
                return list.subList(0, Math.min(list.size(), 200));
            }
        };
    }
}
