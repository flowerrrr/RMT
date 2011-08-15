package de.flower.rmt.service.security;

import de.flower.rmt.model.Users;

/**
 * @author oblume
 */
public interface ISecurityService {

    Users getCurrentUser();

    boolean isCurrentUser(Users user);
}
