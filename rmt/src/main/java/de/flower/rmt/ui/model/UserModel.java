package de.flower.rmt.ui.model;

import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.service.IUserManager;
import org.apache.wicket.model.IModel;
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

    public UserModel() {
        super();
    }

    public UserModel(final IModel<User> model) {
        super(model);
    }

    @Override
    protected User load(Long id) {
        return manager.loadById(id);
    }

    @Override
    protected User newInstance() {
        return manager.newInstance();
    }
}
