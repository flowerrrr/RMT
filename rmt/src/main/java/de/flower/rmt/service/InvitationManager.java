package de.flower.rmt.service;

import de.flower.common.util.Check;
import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.Invitation_;
import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.User;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.repository.IInvitationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.metamodel.Attribute;
import java.util.*;

import static de.flower.rmt.repository.Specs.*;
import static org.springframework.data.jpa.domain.Specifications.where;

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
    @Deprecated
    public List<Invitation> findAllByEvent(final Event event, final Attribute... attributes) {
        Specification fetch = fetch(attributes);
        return invitationRepo.findAll(where(eq(Invitation_.event, event)).and(fetch));
    }

    @Override
    public List<Invitation> findAllByEventSortedByName(final Event event) {
        List<Invitation> list = findAllByEvent(event, Invitation_.user);
        // use in-memory sorting cause field name is derived and would required complicated sql-query to sort after.
        Collections.sort(list, new Comparator<Invitation>() {
            @Override
            public int compare(final Invitation o1, final Invitation o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
        return list;
    }

    @Override
    public List<Invitation> findAllByEventAndStatus(Event event, RSVPStatus rsvpStatus, final Attribute... attributes) {
        return invitationRepo.findAll(where(eq(Invitation_.event, event))
                .and(eq(Invitation_.status, rsvpStatus))
                .and(asc(Invitation_.date))
                .and(fetch(attributes)));
    }

    @Override
    public Long numByEventAndStatus(final Event event, final RSVPStatus rsvpStatus) {
        return invitationRepo.numByEventAndStatus(event, rsvpStatus);
    }

/*
    @Override
    public List<Invitation> findAlByEmails(final Event event, final List<String> addressList) {
        List<Invitation> list = findAllByEvent(event);
        Iterator<Invitation> iterator = list.iterator();
        while (iterator.hasNext()) {
            Invitation invitation = iterator.next();
            if (invitation.getEmail() != null) {
                if (!addressList.contains(invitation.getEmail())) {
                     iterator.remove();
                }
            } else {
                // guest player
                iterator.remove();
            }
        }
        return list;
    }
*/

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
        if (!invitation.isNew()) {
            // in case the status changes update the date of response.
            // used for early maybe-responder who later switch their status.
            // after status update the rank of an invitation is reset as if he has
            // just responded the first time.
            Invitation origInvitation = invitationRepo.findOne(invitation.getId());
            if (origInvitation.getStatus() != invitation.getStatus()) {
                invitation.setDate(new Date());
            }
            if (invitation.getDate() == null) {
                invitation.setDate(new Date());
            }
        }
        return invitationRepo.save(invitation);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(final Long id) {
        invitationRepo.delete(id);
    }

    @Override
    public void markInvitationSent(final Event event, final List<String> addressList) {
        invitationRepo.markInvitationSent(event, addressList);
    }

    @Override
    public void addUsers(final Event entity, final Collection<Long> userIds) {
        for (Long userId : userIds) {
            User user = userManager.loadById(userId);
            Invitation invitation = newInstance(entity, user);
            save(invitation);
        }
    }

    @Override
    public void addGuestPlayer(final Event entity, final String guestName) {
        Invitation invitation = newInstance(entity, guestName);
        save(invitation);
    }
}
