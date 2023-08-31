package de.flower.rmt.service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mysema.query.types.expr.BooleanExpression;
import de.flower.common.util.Check;
import de.flower.rmt.model.db.entity.CalItem;
import de.flower.rmt.model.db.entity.CalItem_;
import de.flower.rmt.model.db.entity.QCalItem;
import de.flower.rmt.model.db.entity.Team;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.dto.CalItemDto;
import de.flower.rmt.repository.ICalItemRepo;
import de.flower.rmt.service.type.CalendarFilter;
import de.flower.rmt.ui.markup.html.calendar.CalEvent;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.metamodel.Attribute;
import java.util.Collection;
import java.util.List;

import static de.flower.rmt.repository.Specs.eq;
import static de.flower.rmt.repository.Specs.fetch;
import static org.springframework.data.jpa.domain.Specifications.where;


@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class CalendarManager extends AbstractService {

    @Autowired
    private ICalItemRepo calItemRepo;

    @Autowired
    private EventManager eventManager;

    @Autowired
    private TeamManager teamManager;

    @Autowired
    private MessageSourceAccessor messageSource;

    @Autowired
    private InvitationManager invitationManager;

    public CalItem loadById(final Long id, Attribute... attributes) {
        Specification fetch = fetch(attributes);
        CalItem entity = calItemRepo.findOne(where(eq(CalItem_.id, id)).and(fetch));
        Check.notNull(entity, "CalItem [" + id + "] not found");
        return entity;
    }

    public void save(final CalItemDto dto, final User user) {
        if (dto.isAllDay()) {
            // set start time to 0:00 and end time to 23:59
            dto.setStartTime(new LocalTime(0, 0));
            dto.setEndTime(new LocalTime(0, 0).minusMillis(1));
        }
        CalItem entity;
        if (dto.isNew()) {
            entity = new CalItem();
            entity.setUser(user);
        } else {
            entity = loadById(dto.getId());
        }
        dto.copyTo(entity);
        validate(entity);
        calItemRepo.save(entity);
        dto.setId(entity.getId());
        if (entity.isAutoDecline()) {
            invitationManager.onAutoDeclineCalItem(entity);
        }
    }

    public List<CalEvent> findAllByCalendarAndRange(List<CalendarFilter> calendarFilters, final DateTime start, final DateTime end) {
        List<?> list = Lists.newArrayList();
        for (CalendarFilter filter : calendarFilters) {
            if (filter.type == CalendarFilter.Type.USER) {
                list.addAll((Collection) findAllByUserAndRange(securityService.getUser(), start, end));
            }
            if (filter.type == CalendarFilter.Type.OTHERS) {
                list.addAll((Collection) findAllByOthersAndRange(start, end));
            }
            if (filter.type == CalendarFilter.Type.CLUB) {
                list.addAll((Collection) eventManager.findAllByDateRange(start, end));
            }
            if (filter.type == CalendarFilter.Type.TEAM) {
                list.addAll((Collection) findAllByTeamAndRange(filter.team, start, end));
            }
        }
        return transform(list);
    }

    public List<CalItem> findAllByUserAndRange(final User user, final DateTime calStart, final DateTime calEnd) {
        BooleanExpression isUser = QCalItem.calItem.user.eq(user);
        BooleanExpression isNotStartAfterCalEnd = QCalItem.calItem.startDateTime.after(calEnd.toDate()).not();
        BooleanExpression isNotEndBeforeCalStart = QCalItem.calItem.endDateTime.before(calStart.toDate()).not();
        return calItemRepo.findAll(isUser.and(isNotEndBeforeCalStart).and(isNotStartAfterCalEnd));
    }

    private List<CalItem> findAllByOthersAndRange(final DateTime calStart, final DateTime calEnd) {
        BooleanExpression isNotCurrentUser = QCalItem.calItem.user.ne(securityService.getUser());
        // NOTE (flowerrrr - 25.06.12) make calitem extend abstractclubrelatedentity and remove this expression
        BooleanExpression isClub = QCalItem.calItem.user.club.eq(getClub());
        BooleanExpression isNotStartAfterCalEnd = QCalItem.calItem.startDateTime.after(calEnd.toDate()).not();
        BooleanExpression isNotEndBeforeCalStart = QCalItem.calItem.endDateTime.before(calStart.toDate()).not();
        return calItemRepo.findAll(isNotCurrentUser.and(isClub).and(isNotEndBeforeCalStart).and(isNotStartAfterCalEnd));
    }

    private List<CalItem> findAllByTeamAndRange(final Team team, final DateTime calStart, final DateTime calEnd) {
        BooleanExpression isTeam = QCalItem.calItem.user.players.any().team.eq(team);
        BooleanExpression isNotStartAfterCalEnd = QCalItem.calItem.startDateTime.after(calEnd.toDate()).not();
        BooleanExpression isNotEndBeforeCalStart = QCalItem.calItem.endDateTime.before(calStart.toDate()).not();
        return calItemRepo.findAll(isTeam.and(isNotEndBeforeCalStart).and(isNotStartAfterCalEnd));
    }

    private List<CalEvent> transform(List<?> items) {
        List<CalEvent> events = Lists.transform(items, new Function<Object, CalEvent>() {
            @Override
            public CalEvent apply(final Object item) {
                if (item instanceof CalItem) {
                    return toCalEvent((CalItem) item);
                } else if (item instanceof Event) {
                    return toCalEvent((Event) item);
                } else {
                    throw new IllegalArgumentException("Unknown type [" + item + "].");
                }
            }
        });
        return Lists.newArrayList(Sets.newHashSet(events)); // filter duplicates
    }

    private CalEvent toCalEvent(final Event event) {
        CalEvent calEvent = new CalEvent();
        calEvent.id = Event.class.getSimpleName() + "|" + event.getId();
        calEvent.entityId = event.getId();
        calEvent.clazzName = Event.class.getName();
        calEvent.title = event.getTeam().getName() + ": " + messageSource.getMessage(event.getEventType().getResourceKey());
        calEvent.start = event.getDateTime().toDate();
        calEvent.end = event.getDateTimeEnd().toDate();
        calEvent.allDay = false;
        calEvent.className = "cal-type-event";
        // calEvent.url = urlProvider.deepLinkEvent(event.getId());
        return calEvent;
    }

    private CalEvent toCalEvent(CalItem calItem) {
        CalEvent calEvent = new CalEvent();
        calEvent.id = CalItem.class.getSimpleName() + "|" + calItem.getId();
        calEvent.entityId = calItem.getId();
        calEvent.clazzName = CalItem.class.getName();
        if (calItem.getType() == CalItem.Type.OTHER) {
            calEvent.title = (calItem.getSummary() == null) ? "" : calItem.getSummary();
        } else {
            calEvent.title = messageSource.getMessage(CalItem.Type.getResourceKey(calItem.getType()));
            calEvent.title += (calItem.getSummary() == null) ? "" : ": " + calItem.getSummary();
        }
        calEvent.title = calItem.getUser().getFullname() + ": " + calEvent.title;
        calEvent.start = calItem.getStartDateTime().toDate();
        calEvent.end = calItem.getEndDateTime().toDate();
        calEvent.allDay = calItem.isAllDay();
        calEvent.className = (securityService.isCurrentUser(calItem.getUser())) ? "cal-type-user" : "cal-type-others";
        return calEvent;
    }

    public void delete(final Long id) {
        CalItem entity = loadById(id);
        // no security assertions yet.
        calItemRepo.delete(entity);
    }

    public List<CalendarFilter> getCalendarFilters() {
        List<CalendarFilter> filters = Lists.newArrayList();
        filters.add(CalendarFilter.USER);
        filters.add(CalendarFilter.CLUB);
        filters.add(CalendarFilter.OTHERS);
        for (Team team : teamManager.findAll()) {
            filters.add(new CalendarFilter(CalendarFilter.Type.TEAM, team));
        }
        return filters;
    }
}
