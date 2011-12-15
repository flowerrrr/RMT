package de.flower.rmt.service;

import de.flower.common.util.Check;
import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.User;
import de.flower.rmt.model.event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author flowerrrr
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class ResponseManager extends AbstractService implements IResponseManager {

    @Autowired
    private IEventManager eventManager;

    @Autowired
    private IUserManager userManager;

    @Autowired
    private IInvitationManager invitationManager;

    @Override
    @Transactional(readOnly = false)
    public Invitation respond(Event event, User user, RSVPStatus status, String comment) {
        // TODO (flowerrrr - 23.10.11) do some checking if responding to event is allowed
        Invitation invitation = invitationManager.loadByEventAndUser(event, user);
        Check.notNull(invitation, "No invitation found.");
        invitation.setStatus(status);
        if (comment != null) {
            invitation.setComment(comment);
        }
        return invitationManager.save(invitation);
    }

    @Override
    public Invitation respond(final Long eventId, final Long userId, final RSVPStatus status) {
        Check.notNull(eventId);
        Check.notNull(userId);
        Event event = eventManager.loadById(eventId);
        User user = userManager.loadById(userId);
        return respond(event, user, status, null);
    }
}
