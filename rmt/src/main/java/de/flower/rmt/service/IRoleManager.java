package de.flower.rmt.service;

import de.flower.rmt.model.User;

/**
 * @author flowerrrr
 */
public interface IRoleManager {

    public void addRole(Long userId, String roleName);

    public void removeRole(Long userId, String roleName);

    boolean isManager(User user);
}
