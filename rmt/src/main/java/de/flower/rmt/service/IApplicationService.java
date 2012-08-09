package de.flower.rmt.service;

import de.flower.rmt.model.db.entity.User;

/**
 * @author flowerrrr
 */
public interface IApplicationService {

    String getMessageOfTheDay();

    String getProperty(String name);

    String getUserProperty(User user, String name);

    void saveUserProperty(User user, String name, String value);

}
