package de.flower.rmt.service.security;

import de.flower.rmt.model.User;
import de.flower.rmt.service.IUserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Custom implementation of spring's UserDetailService in order to use our own User domain entity as principal.
 *
 * @author flowerrrr
 */
@Service("userDetailService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class UserDetailServiceBean implements UserDetailsService {

    @Autowired
    private IUserManager userManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        User user = userManager.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username {" + username + "} not found");
        }
        user.getClub(); // init field cause we need it very often.
        return new UserDetailsBean(user);
    }
}
