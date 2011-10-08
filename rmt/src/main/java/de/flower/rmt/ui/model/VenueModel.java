package de.flower.rmt.ui.model;

import de.flower.rmt.model.Venue;
import de.flower.rmt.service.IVenueManager;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class VenueModel extends LoadableDetachableModel<Venue> {

    @SpringBean
    private IVenueManager manager;

    private Long id;

    public VenueModel(Venue entity) {
        if (entity != null) {
            setObject(entity);
            this.id = entity.getId();
        }
        Injector.get().inject(this);
    }

    @Override
    protected Venue load() {
        if (id == null) {
            return manager.newVenueInstance();
        } else {
            return manager.findById(id);
        }
    }
}
