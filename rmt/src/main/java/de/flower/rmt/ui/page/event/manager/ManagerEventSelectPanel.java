package de.flower.rmt.ui.page.event.manager;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.Event_;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.ui.page.event.EventSelectPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
@Deprecated
public abstract class ManagerEventSelectPanel extends EventSelectPanel {

    @SpringBean
    private IEventManager eventManager;

    public ManagerEventSelectPanel(final IModel<Event> model) {
        super(model);
        setListModel(getListModel());
    }

    private IModel<List<Event>> getListModel() {
        return new LoadableDetachableModel<List<Event>>() {
            @Override
            protected List<Event> load() {
                return eventManager.findAll(Event_.team);
            }
        };
    }



}
