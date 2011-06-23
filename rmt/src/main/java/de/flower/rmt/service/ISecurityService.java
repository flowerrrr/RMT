package de.flower.rmt.service;

import de.flower.rmt.model.Users;

/**
 * @author oblume
 */
public interface ISecurityService {

    /**
     * Returns the currently logged in user (the one stored in the securitycontext).
     * @return
     */
    Users getCurrentUser();
}
