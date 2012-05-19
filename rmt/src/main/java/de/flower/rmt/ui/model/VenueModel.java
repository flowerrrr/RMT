package de.flower.rmt.ui.model;

import de.flower.rmt.model.db.entity.Venue;
import de.flower.rmt.service.IVenueManager;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class VenueModel extends AbstractEntityModel<Venue> {

    @SpringBean
    private IVenueManager manager;

    public VenueModel(Venue entity) {
        super(entity);
    }

    public VenueModel() {
       super();
    }

    public VenueModel(final IModel<Venue> model) {
        super(model);
    }

    @Override
    protected Venue load(Long id) {
        return manager.loadById(id);
    }

    @Override
    protected Venue newInstance() {
        return manager.newInstance();
    }
}
