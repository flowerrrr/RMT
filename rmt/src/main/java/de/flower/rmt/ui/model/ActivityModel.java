package de.flower.rmt.ui.model;

import de.flower.rmt.model.Activity;
import de.flower.rmt.service.IActivityManager;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class ActivityModel extends AbstractEntityModel<Activity> {

    @SpringBean
    private IActivityManager manager;

   public ActivityModel(Activity entity) {
        super(entity);
     }

    @Override
    protected Activity load(Long id) {
        return manager.loadById(id);
    }

    @Override
    protected Activity newInstance() {
        throw new UnsupportedOperationException("Model should only be used for persisted entities!");
    }

}
