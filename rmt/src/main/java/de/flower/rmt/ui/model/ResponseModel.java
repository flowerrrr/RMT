package de.flower.rmt.ui.model;

import de.flower.common.util.Check;
import de.flower.rmt.model.Response;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IResponseManager;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class ResponseModel extends AbstractEntityModel<Response> {

    @SpringBean
    private IResponseManager manager;

    private IModel<Event> eventModel;

    public ResponseModel(Response entity) {
        super(entity);
        Check.notNull(entity);
    }

    public ResponseModel(Event event) {
        this.eventModel = new EventModel(event);
    }

    public ResponseModel(final IModel<Event> model) {
        this.eventModel = model;
    }

    @Override
    protected Response load(Long id) {
        return manager.loadById(id);
    }

    @Override
    protected Response newInstance() {
        return manager.newInstance(eventModel.getObject());
    }

    @Override
    protected void onDetach() {
        if (eventModel != null) {
            eventModel.detach();
        }
        super.onDetach();
    }
}
