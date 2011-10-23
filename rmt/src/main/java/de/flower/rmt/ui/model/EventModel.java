package de.flower.rmt.ui.model;

import de.flower.common.util.Check;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.EventType;
import de.flower.rmt.service.IEventManager;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class EventModel extends AbstractEntityModel<Event> {

    @SpringBean
    private IEventManager manager;

    private EventType type;

    public EventModel(Event entity) {
        super(entity);
        Check.notNull(entity);
        this.type = EventType.from(entity);
    }

    @Override
    protected Event load(Long id) {
        return manager.findById(id);
    }

    @Override
    protected Event newInstance() {
        return manager.newInstance(type);
    }
}
