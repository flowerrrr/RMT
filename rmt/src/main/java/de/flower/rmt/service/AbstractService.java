package de.flower.rmt.service;

import de.flower.rmt.model.Club;
import de.flower.rmt.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import static com.google.common.base.Preconditions.*;

/**
 * @author oblume
 */
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public abstract class AbstractService {

    @Autowired
    private ISecurityService securityService;

    /**
     * Returns club of currently logged in user.
     *
     * @return
     */
    protected Club getClub() {
        Users currentUser = securityService.getCurrentUser();
        checkNotNull(currentUser, "Security Context must be filled with a user");
        return currentUser.getClub();
    }


}
