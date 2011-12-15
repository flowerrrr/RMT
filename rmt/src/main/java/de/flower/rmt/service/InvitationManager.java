package de.flower.rmt.service;

import de.flower.common.util.Check;
import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.User;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.repository.IInvitationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author flowerrrr
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class InvitationManager extends AbstractService implements IInvitationManager {

    @Autowired
    private IInvitationRepo invitationRepo;

    @Autowired
    private IPlayerManager playerManager;

    @Autowired
    private IEventManager eventManager;

    @Autowired
    private IUserManager userManager;

    @Override
    public Invitation newInstance(final Event event, User user) {
        Check.notNull(event);
        Check.notNull(user);
        return new Invitation(event, user);
    }

    @Override
    public Invitation newInstance(final Event event, String guestName) {
        Check.notNull(event);
        Check.notBlank(guestName);
        return new Invitation(event, guestName);
    }

    @Override
    public Invitation loadById(final Long id) {
        return Check.notNull(invitationRepo.findOne(id));
    }

    @Override
    public List<Invitation> findByEvent(final Event event) {
        return invitationRepo.findByEvent(event);
    }

    @Override
    public List<Invitation> findByEventAndStatus(Event event, RSVPStatus rsvpStatus) {
        return invitationRepo.findByEventAndStatusOrderByDateAsc(event, rsvpStatus);
    }

    @Override
    public Long numByEventAndStatus(final Event event, final RSVPStatus rsvpStatus) {
        return invitationRepo.numByEventAndStatus(event, rsvpStatus);
    }

    @Override
    public Invitation loadByEventAndUser(Event event, User user) {
        Invitation invitation = invitationRepo.findByEventAndUser(event, user);
        Check.notNull(invitation, "No entity found");
        return invitation;
    }

    @Override
    @Transactional(readOnly = false)
    public Invitation save(final Invitation invitation) {
        validate(invitation);
        if (invitation.isNew()) {
            invitation.setDate(new Date());
        } else {
            // in case the status changes update the date of response.
            // used for early maybe-responder who later switch their status.
            // after status update the rank of an invitation is reset as if he has
            // just responded the first time.
            Invitation origInvitation = invitationRepo.findOne(invitation.getId());
            if (origInvitation.getStatus() != invitation.getStatus()) {
                invitation.setDate(new Date());
            }
        }
        return invitationRepo.save(invitation);
    }

 }
