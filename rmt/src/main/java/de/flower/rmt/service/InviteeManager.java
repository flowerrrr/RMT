package de.flower.rmt.service;

import de.flower.common.util.Check;
import de.flower.rmt.model.Invitee;
import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.User;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.repository.IInviteeRepo;
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
public class InviteeManager extends AbstractService implements IInviteeManager {

    @Autowired
    private IInviteeRepo inviteeRepo;

    @Autowired
    private IPlayerManager playerManager;

    @Autowired
    private IEventManager eventManager;

    @Autowired
    private IUserManager userManager;

    @Override
    public Invitee newInstance(final Event event, User user) {
        Check.notNull(event);
        Check.notNull(user);
        return new Invitee(event, user);
    }

    @Override
    public Invitee newInstance(final Event event, String guestName) {
        Check.notNull(event);
        Check.notBlank(guestName);
        return new Invitee(event, guestName);
    }

    @Override
    public Invitee loadById(final Long id) {
        return Check.notNull(inviteeRepo.findOne(id));
    }

    @Override
    public List<Invitee> findByEvent(final Event event) {
        return inviteeRepo.findByEvent(event);
    }

    @Override
    public List<Invitee> findByEventAndStatus(Event event, RSVPStatus rsvpStatus) {
        return inviteeRepo.findByEventAndStatusOrderByDateAsc(event, rsvpStatus);
    }

    @Override
    public Long numByEventAndStatus(final Event event, final RSVPStatus rsvpStatus) {
        return inviteeRepo.numByEventAndStatus(event, rsvpStatus);
    }

    @Override
    public Invitee loadByEventAndUser(Event event, User user) {
        Invitee invitee = inviteeRepo.findByEventAndUser(event, user);
        Check.notNull(invitee, "No entity found");
        return invitee;
    }

    @Override
    @Transactional(readOnly = false)
    public Invitee save(final Invitee invitee) {
        validate(invitee);
        if (invitee.isNew()) {
            invitee.setDate(new Date());
        } else {
            // in case the status changes update the date of response.
            // used for early maybe-responder who later switch their status.
            // after status update the rank of an invitee is reset as if he has
            // just responded the first time.
            Invitee origInvitee = inviteeRepo.findOne(invitee.getId());
            if (origInvitee.getStatus() != invitee.getStatus()) {
                invitee.setDate(new Date());
            }
        }
        return inviteeRepo.save(invitee);
    }

 }
