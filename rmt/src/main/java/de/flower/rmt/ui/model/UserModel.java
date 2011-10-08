package de.flower.rmt.ui.model;

import de.flower.rmt.model.User;
import de.flower.rmt.service.IUserManager;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class UserModel extends AbstractEntityModel<User> {

    @SpringBean
    private IUserManager manager;

    public UserModel(User entity) {
        super(entity);
    }

    @Override
    protected User load(Long id) {
        if (id == null) {
            return manager.newUserInstance();
        } else {
            return manager.findById(id);
        }
    }
}
