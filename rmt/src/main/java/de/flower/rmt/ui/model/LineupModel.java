package de.flower.rmt.ui.model;

import de.flower.rmt.model.db.entity.Lineup;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.service.LineupManager;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;


public class LineupModel extends LoadableDetachableModel<Lineup> {

    @SpringBean
    private LineupManager manager;

    private IModel<Event> eventModel;

    public LineupModel(IModel<Event> eventModel) {
        this.eventModel = eventModel;
        Injector.get().inject(this);
    }

    @Override
    protected Lineup load() {
        return manager.findOrCreateLineup(eventModel.getObject());
    }

    @Override
    public void detach() {
        super.detach();
        eventModel.detach();
    }
}
