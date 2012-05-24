package de.flower.rmt.service;

import com.mysema.query.types.EntityPath;
import com.mysema.query.types.expr.BooleanExpression;
import de.flower.common.model.EntityHelper;
import de.flower.common.util.Check;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.Team;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.entity.event.Event_;
import de.flower.rmt.model.db.entity.event.QEvent;
import de.flower.rmt.model.db.type.EventType;
import de.flower.rmt.model.db.type.activity.EventUpdateMessage;
import de.flower.rmt.model.dto.Notification;
import de.flower.rmt.repository.IEventRepo;
import de.flower.rmt.service.mail.IMailService;
import org.apache.commons.lang3.ArrayUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.metamodel.Attribute;
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

    @Autowired
    private IActivityManager activityManager;

    @Value("${event.closed.before.hours}")
    private Integer eventClosedBeforeHours;

    @Autowired
    private MessageSourceAccessor messageSource;

    @Override
    @Transactional(readOnly = false)
    public void save(Event entity) {
        validate(entity);
        EventUpdateMessage.Type type = (entity.isNew()) ? EventUpdateMessage.Type.CREATED : EventUpdateMessage.Type.UPDATED;
        eventRepo.save(entity);
        activityManager.onCreateOrUpdateEvent(entity, type);
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
        Event entity = eventRepo.findOne(where(eq(Event_.id, id)).and(fetch));
        Check.notNull(entity, "Event [" + id + "] not found");
        assertClub(entity);
        // init transient date fields
        entity.initTransientFields();
        return entity;
    }

    @Override
    public List<Event> findAll(Attribute... attributes) {
        List<Event> list = eventRepo.findAll(where(desc(Event_.dateTime)).and(fetch(attributes)));
        return list;
    }

    // @Override
    public List<Event> findAllUpcomingAndLastNByManager(Attribute... attributes) {
        throw new UnsupportedOperationException("Method not implemented!");
    }

    private List<Event> findAllUpcomingByUser(final User user, EntityPath<?>... attributes) {
        Check.notNull(user);
        BooleanExpression future = QEvent.event.dateTime.after(new DateTime());
        BooleanExpression isUser = QEvent.event.invitations.any().user.eq(user);
        return eventRepo.findAll(future.and(isUser), QEvent.event.dateTime.desc(), attributes);
    }

    private List<Event> findLastNByUser(final User user, final int num, EntityPath<?>... attributes) {
        Check.notNull(user);
        BooleanExpression beforeNow = QEvent.event.dateTime.before(new DateTime());
        BooleanExpression isUser = QEvent.event.invitations.any().user.eq(user);
        return eventRepo.findAll(beforeNow.and(isUser), new PageRequest(0, num, Sort.Direction.DESC, Event_.dateTime.getName()), attributes).getContent();
    }

    @Override
    public List<Event> findAllUpcomingAndLastNByUser(final User user, int num, EntityPath<?>... attributes) {
        List<Event> upcoming = findAllUpcomingByUser(user, attributes);
        List<Event> lastN = findLastNByUser(user, num, attributes);
        upcoming.addAll(lastN);
        return upcoming;
    }

    @Override
    public List<Event> findAllNextNHours(final int hours) {
        // when: 5 days before event, but at least 48 h after invitation mail
        DateTime now = new DateTime();
        BooleanExpression insideNextNDays = QEvent.event.dateTime.between(now, now.plusHours(hours));
        BooleanExpression notCanceled = QEvent.event.canceled.ne(true);
        return eventRepo.findAll(insideNextNDays.and(notCanceled));
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
        event.setCreatedBy(securityService.getUser());
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
        invitationManager.markInvitationSent(event, notification.getAddressList(), null);
        activityManager.onInvitationMailSent(event);
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

    @Override
    public boolean isEventClosed(Event event) {
        DateTime now = new DateTime();
        eventRepo.reattach(event);
        return now.minusHours(eventClosedBeforeHours).isAfter(event.getDateTime());
    }

    @Override
    public void cancelEvent(final Long id) {
        Event event = loadById(id);
        event.setCanceled(true);
        String summary = messageSource.getMessage("event.summary.canceled.prefix") + " " + event.getSummary();
        event.setSummary(summary);
        eventRepo.save(event);

        // notify accepted and unsure invitees
        sendCancelationMail(event);

        activityManager.onCreateOrUpdateEvent(event, EventUpdateMessage.Type.CANCELED);
    }

    private void sendCancelationMail(final Event event) {
        log.info("Send cancelation mail ... not yet implemented");
    }
}
