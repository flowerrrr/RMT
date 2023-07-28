package de.flower.rmt.service;

import de.flower.common.util.Check;
import de.flower.rmt.model.db.entity.AbstractClubRelatedEntity;
import de.flower.rmt.model.db.entity.Club;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

import static de.flower.common.util.Check.*;


/**
 * @author flowerrrr
 */
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public abstract class AbstractService {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected SecurityService securityService;

    @Autowired
    private Validator validator;


    /**
     * Returns club of currently logged in user.
     *
     * @return
     */
    protected Club getClub() {
        User currentUser = securityService.getUser();
        notNull(currentUser, "Security Context must be filled with a user");
        return Check.notNull(currentUser.getClub());
    }

    protected void assertClub(AbstractClubRelatedEntity entity) {
        if (entity != null) {
            Check.isEqual(entity.getClub(), getClub(), null);
        }
    }

    protected void validate(Object entity) throws ConstraintViolationException {

        Set<?> violations = validator.validate(entity);
        if (!violations.isEmpty()) {
            String message = violations.toString();
            //noinspection unchecked
            throw new ConstraintViolationException(message, (Set<ConstraintViolation<?>>) violations);
        }
    }

}
