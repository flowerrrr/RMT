package de.flower.rmt.service;

import de.flower.rmt.model.Users;
import de.flower.rmt.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.stereotype.Service;

/**
 * @author oblume
 */
@Service
public class SecurityService implements ISecurityService {

    @Autowired
    private SecurityContextHolderStrategy schs;

    @Autowired
    private IUserRepo userRepo;


    @Override
    public Users getCurrentUser() {
        String username = schs.getContext().getAuthentication().getName();
        Users user = userRepo.findByUsername(username);
        return user;
    }
}
