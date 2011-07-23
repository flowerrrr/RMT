package de.flower.rmt.service;

import de.flower.rmt.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author oblume
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class SecurityService implements ISecurityService {

    @Autowired
    private SecurityContextHolderStrategy schs;

    @Autowired
    private IUserManager userManager;


    public Users getCurrentUser() {
        String username = schs.getContext().getAuthentication().getName();
        // TODO (oblume - 23.07.11) - if lookup to slow then cache user object in security context
        Users user = userManager.findByUsername(username);
        return user;
    }

}
