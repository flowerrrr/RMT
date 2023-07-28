package de.flower.rmt.ui.model;

import de.flower.rmt.model.db.entity.Activity;
import de.flower.rmt.service.ActivityManager;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class ActivityModel extends AbstractEntityModel<Activity> {

    @SpringBean
    private ActivityManager manager;

   public ActivityModel(Activity entity) {
        super(entity);
     }

    @Override
    protected Activity load(Long id) {
        return manager.loadById(id);
    }

}
