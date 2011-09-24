package de.flower.rmt.service.security;

import de.flower.rmt.model.User;

/**
 * @author flowerrrr
 */
public interface ISecurityService {

    User getCurrentUser();

    boolean isCurrentUser(User user);
}
