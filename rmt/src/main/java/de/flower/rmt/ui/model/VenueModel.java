package de.flower.rmt.ui.model;

import de.flower.rmt.model.Venue;
import de.flower.rmt.service.IVenueManager;
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

    @Override
    protected Venue load(Long id) {
        if (id == null) {
            return manager.newVenueInstance();
        } else {
            return manager.findById(id);
        }
    }
}
