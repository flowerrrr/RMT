package de.flower.rmt.service.security;

import de.flower.rmt.model.Users;
import de.flower.rmt.service.IUserManager;
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


    @Override
    public Users getCurrentUser() {
        Object o = schs.getContext().getAuthentication().getPrincipal();
        if (o instanceof UserDetailsBean) {
            UserDetailsBean principal = (UserDetailsBean) o;
            return principal.getUser();
        } else {
            // anonymous user.
            return null;
        }
    }

    @Override
    public boolean isCurrentUser(Users user) {
        return getCurrentUser().equals(user);
    }
}
