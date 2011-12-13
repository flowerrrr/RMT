package de.flower.rmt.service.security;

import de.flower.rmt.model.User;

/**
 * @author flowerrrr
 */
public interface ISecurityService {

    UserDetailsBean getCurrentUser();

    boolean isCurrentUser(User user);

    User getUser();
}
