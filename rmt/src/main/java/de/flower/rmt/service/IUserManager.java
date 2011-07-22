package de.flower.rmt.service;

import de.flower.rmt.model.Users;

import javax.persistence.metamodel.Attribute;
import java.util.List;

/**
 * @author oblume
 */
public interface IUserManager {

    Users findByUsername(String username);

    void save(Users user);

    // List<Users> findAll();

    void delete(Users user);

    List<Users> findAll(Attribute... attributes);

    Users newPlayerInstance();
}
