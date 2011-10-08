package de.flower.rmt.ui.model;

import de.flower.rmt.model.User;
import de.flower.rmt.service.IUserManager;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class UserModel extends LoadableDetachableModel<User> {

    @SpringBean
    private IUserManager manager;

    private Long id;

    public UserModel(User entity) {
        if (entity != null) {
            setObject(entity);
            this.id = entity.getId();
        }
        Injector.get().inject(this);
    }

    @Override
    protected User load() {
        if (id == null) {
            return manager.newUserInstance();
        } else {
            return manager.findById(id);
        }
    }
}
