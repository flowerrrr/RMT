package de.flower.rmt.service;

import de.flower.common.util.Check;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.type.RSVPStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service used for quick response label.
 * Could be removed and replaced by InvitationManager.
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class ResponseManager extends AbstractService {

    @Autowired
    private EventManager eventManager;

    @Autowired
    private UserManager userManager;

    @Autowired
    private InvitationManager invitationManager;

    public Invitation respond(final Long eventId, final Long userId, final RSVPStatus status) {
        Check.notNull(eventId);
        Check.notNull(userId);
        Event event = eventManager.loadById(eventId);
        User user = userManager.loadById(userId);
        Invitation invitation = invitationManager.loadByEventAndUser(event, user);
        Check.notNull(invitation, "No invitation found.");
        invitation.setStatus(status);
        invitationManager.save(invitation);
        return invitation;
    }
}
