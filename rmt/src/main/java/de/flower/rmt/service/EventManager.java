package de.flower.rmt.service;

import de.flower.common.model.EntityHelper;
import de.flower.common.util.Check;
import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.Team;
import de.flower.rmt.model.User;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.EventType;
import de.flower.rmt.model.event.Event_;
import de.flower.rmt.model.type.Notification;
import de.flower.rmt.repository.IEventRepo;
import de.flower.rmt.service.mail.IMailService;
import org.apache.commons.lang3.ArrayUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.metamodel.Attribute;
import java.util.Date;
import java.util.List;

import static de.flower.rmt.repository.Specs.*;
import static org.springframework.data.jpa.domain.Specifications.where;

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

    @Autowired
    private IMailService mailService;

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
            List<User> users = userManager.findAllByTeam(entity.getTeam());
            invitationManager.addUsers(entity, EntityHelper.convertEntityListToIdList(users));
        }
    }

    @Override
    public Event loadById(Long id, final Attribute... attributes) {
        Specification fetch = fetch(attributes);
        Event entity = eventRepo.findOne(and(eq(Event_.id, id), fetch));
        Check.notNull(entity, "No event found");
        assertClub(entity);
        // for some reason i don't know the fetching of surfaceList works unreliable.
        // sometimes the association is initialized, sometimes not. very strange.
//        if (ArrayUtils.contains(attributes, AbstractSoccerEvent_.surfaceList)) {
//            ((AbstractSoccerEvent) entity).getSurfaceList().isEmpty();
//        }
        return entity;
    }

    @Override
    public List<Event> findAll(Attribute... attributes) {
        List<Event> list = eventRepo.findAll(where(desc(Event_.date)).and(fetch(attributes)));
        return list;
    }

    @Override
    public List<Event> findAllUpcomingByUser(final User user) {
        Check.notNull(user);
        Date date = new LocalDate().toDate();
        return eventRepo.findAllUpcomingByInvitee(user, date);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        Event entity = loadById(id);
        assertClub(entity);
        List<Invitation> invitations = invitationManager.findAllByEvent(entity);
        for (Invitation invitation : invitations) {
            invitationManager.delete(invitation.getId());
        }
        eventRepo.delete(entity);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteByTeam(Team team) {
        for (Event event : eventRepo.findAllByTeam(team)) {
            eventRepo.softDelete(event);
        }
    }

    @Override
    public Event newInstance(EventType eventType) {
        Check.notNull(eventType);
        Event event = eventType.newInstance(getClub());
        return event;
    }

    @Override
    public Event loadByIdAndUser(final Long id, final User user) {
        Event event = loadById(id);
        // is this event related to the club of the user?
        Check.isEqual(event.getClub(), user.getClub());
        // is user an invitee of this event
        Invitation invitation = invitationManager.loadByEventAndUser(event, user);
        return event;
    }

    @Override
    public void sendInvitationMail(final Long eventId, final Notification notification) {
        Event event = loadById(eventId);
        mailService.sendMassMail(notification);
        event.setInvitationSent(true);
        eventRepo.save(event);
        // update all invitations and mark them also with invitationSent = true (allows to later add invitations and send mails to new participants)
        invitationManager.markInvitationSent(event, notification.getAddressList());
    }

    /**
     * Initializes (fetches) associations of entity.
     * Not sure if this way or  #loadById(event.getId(), attributes) is better, faster, more reliable.
     */
    @Override
    @Deprecated // experimental
    public Event initAssociations(final Event event, final Attribute... attributes) {
        eventRepo.reattach(event);
        if (ArrayUtils.contains(attributes, Event_.team)) {
            event.getTeam().getName();
        }
        if (ArrayUtils.contains(attributes, Event_.venue)) {
            event.getVenue().getName();
        }
        return event;
    }
}
