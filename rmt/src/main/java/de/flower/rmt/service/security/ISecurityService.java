package de.flower.rmt.service.security;

import de.flower.rmt.model.db.entity.User;

/**
 * @author flowerrrr
 */
public interface ISecurityService {

    UserDetailsBean getCurrentUser();

    boolean isCurrentUserLoggedIn();

    boolean isCurrentUser(User user);

    boolean isCurrentUserOrManager(User user);

    User getUser();
}
