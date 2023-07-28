package de.flower.rmt.service.security;

import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.db.entity.User_;
import de.flower.rmt.security.UserDetailsBean;
import de.flower.rmt.service.UserManager;
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
    private UserManager userManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        // init club association cause we need it very often.
        User user = userManager.findByUsername(username, User_.club);
        if (user == null) {
            throw new UsernameNotFoundException("Username {" + username + "} not found");
        }
        return new UserDetailsBean(user);
    }
}
