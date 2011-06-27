package de.flower.rmt.service;

import de.flower.rmt.model.Users;

/**
 * @author oblume
 */
public interface IUserManager {

    Users findByUsername(String username);
}
