package de.flower.rmt.service;

import de.flower.common.util.Check;
import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.User;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.EventType;
import de.flower.rmt.model.event.Event_;
import de.flower.rmt.repository.IEventRepo;
import de.flower.rmt.repository.Specs;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.metamodel.Attribute;
import java.util.Date;
import java.util.List;

/**
 * @author flowerrrr
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EventManager extends AbstractService implements IEventManager {

    @Autowired
    private IEventRepo eventRepo;

    @Autowired
    private IInvitationManager invitationManager;

    @Autowired
    private IUserManager userManager;

    @Override
    @Transactional(readOnly = false)
    public void save(Event entity) {
        validate(entity);
        eventRepo.save(entity);
    }

    @Override
    @Transactional(readOnly = false)
    public void create(final Event entity, final boolean createInvitations) {
        Check.notNull(entity);
        save(entity);
        if (createInvitations) {
            // for every user that is a player of the team of this event a invitation will be created
            List<User> users = userManager.findByTeam(entity.getTeam());
            for (User user : users) {
                // TODO (flowerrrr - 15.12.11) replace by one call to invitationManager
                Invitation invitation = invitationManager.newInstance(entity, user);
                invitationManager.save(invitation);
            }
        }
    }

    @Override
    public Event loadById(Long id, final Attribute... attributes) {
        Specification fetch = Specs.fetch(attributes);
        Event entity = eventRepo.findOne(Specs.and(Specs.eq(Event_.id, id), fetch));
        Check.notNull(entity);
        assertClub(entity);
        return entity;
    }

    @Override
    public List<Event> findAll() {
        return eventRepo.findAllByClub(getClub());
    }

    @Override
    public List<Event> findUpcomingByUser(final User user) {
        Check.notNull(user);
        Date date = new LocalDate().toDate();
        return eventRepo.findUpcomingByInvitee(user, date);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Event entity) {
        // TODO (flowerrrr - 11.06.11) decide whether to soft or hard delete entity.
        assertClub(entity);
        eventRepo.delete(entity);
    }

    @Override
    public Event newInstance(EventType eventType) {
        Check.notNull(eventType);
        try {
            Event event = eventType.getClazz().newInstance();
            event.setClub(getClub());
            return event;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Event loadByIdAndUser(final Long id, final User user) {
        Event event = loadById(id);
        // is this event related to the club of the user?
        Check.isEqual(event.getClub(), user.getClub());
        // is user an invitation of this event
        Invitation invitation = invitationManager.loadByEventAndUser(event, user);
        Check.notNull(invitation);
        return event;
    }
}
