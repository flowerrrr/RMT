package de.flower.rmt.service;

/**
 * @author flowerrrr
 */
public interface IRoleManager {

    public void addRole(Long userId, String roleName);

    public void removeRole(Long userId, String roleName);

    public boolean isManager(Long userId);

}
