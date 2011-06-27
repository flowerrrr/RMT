package de.flower.rmt.service;

import de.flower.rmt.model.Users;
import de.flower.rmt.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author oblume
 */
@Service
public class UserManager implements IUserManager {

    @Autowired
    private IUserRepo userRepo;

    @Override
    public Users findByUsername(String username) {
        return userRepo.findByUsername(username);
    }
}
