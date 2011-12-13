package de.flower.rmt.model.type;

import de.flower.common.model.IEntity;
import de.flower.rmt.model.User;

import javax.validation.Valid;

/**
 * @author flowerrrr
 */
public class UserDto implements IEntity {

    private Boolean manager;

    @Valid
    private User user;

    public UserDto(User user, boolean manager) {
        this.user = user;
        this.manager = manager;
    }

    public Boolean getManager() {
        return manager;
    }

    public void setManager(final Boolean manager) {
        this.manager = manager;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    @Override
    public boolean isNew() {
        return user.isNew();
    }

    @Override
    public Long getId() {
        return user.getId();
    }
}
