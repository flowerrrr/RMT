package de.flower.rmt.service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.mysema.query.types.expr.BooleanExpression;
import de.flower.common.util.Check;
import de.flower.rmt.model.db.entity.CalItem;
import de.flower.rmt.model.db.entity.CalItem_;
import de.flower.rmt.model.db.entity.QCalItem;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.type.CalendarType;
import de.flower.rmt.model.dto.CalItemDto;
import de.flower.rmt.repository.ICalItemRepo;
import de.flower.rmt.ui.markup.html.calendar.CalEvent;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import javax.persistence.metamodel.Attribute;
import java.util.Collection;
import java.util.List;

import static de.flower.rmt.repository.Specs.eq;
import static de.flower.rmt.repository.Specs.fetch;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * @author flowerrrr
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class CalendarManager extends AbstractService implements ICalendarManager {

    @Autowired
    private ICalItemRepo calItemRepo;

    @Autowired
    private IEventManager eventManager;

    @Autowired
    private MessageSourceAccessor messageSource;

    @Autowired
    private ILinkProvider linkProvider;

    @Override
    public CalItem loadById(final Long id, Attribute... attributes) {
        Specification fetch = fetch(attributes);
        CalItem entity = calItemRepo.findOne(where(eq(CalItem_.id, id)).and(fetch));
        Check.notNull(entity, "CalItem [" + id + "] not found");
        return entity;
    }

    @Override
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
    }

    @Override
    public List<CalEvent> findAllByCalendarAndRange(List<CalendarType> calendarTypes, final DateTime start, final DateTime end) {
        List<?> list = Lists.newArrayList();
        if (calendarTypes.contains(CalendarType.USER)) {
            list.addAll((Collection) findAllByUserAndRange(securityService.getUser(), start, end));
        }
        if (calendarTypes.contains(CalendarType.OTHERS)) {
            list.addAll((Collection) findAllByOthersAndRange(start, end));
        }
        if (calendarTypes.contains(CalendarType.CLUB)) {
            list.addAll((Collection) eventManager.findAllByDateRange(start, end));
        }
        return transform(list);
    }

    @Override
    public List<CalItem> findAllByUserAndRange(final User user, final DateTime calStart, final DateTime calEnd) {
        BooleanExpression isUser = QCalItem.calItem.user.eq(user);
        BooleanExpression isNotStartAfterCalEnd = QCalItem.calItem.startDateTime.after(calEnd).not();
        BooleanExpression isNotEndBeforeCalStart = QCalItem.calItem.endDateTime.before(calStart).not();
        return calItemRepo.findAll(isUser.and(isNotEndBeforeCalStart).and(isNotStartAfterCalEnd));
    }

    private List<CalItem> findAllByOthersAndRange(final DateTime calStart, final DateTime calEnd) {
        BooleanExpression isNotCurrentUser = QCalItem.calItem.user.ne(securityService.getUser());
        // NOTE (flowerrrr - 25.06.12) make calitem extend abstractclubrelatedentity and remove this expression
        BooleanExpression isClub = QCalItem.calItem.user.club.eq(getClub());
        BooleanExpression isNotStartAfterCalEnd = QCalItem.calItem.startDateTime.after(calEnd).not();
        BooleanExpression isNotEndBeforeCalStart = QCalItem.calItem.endDateTime.before(calStart).not();
        return calItemRepo.findAll(isNotCurrentUser.and(isClub).and(isNotEndBeforeCalStart).and(isNotStartAfterCalEnd));
    }

    private List<CalEvent> transform(List<?> items) {
        List<CalEvent> events = Lists.transform(items, new Function<Object, CalEvent>() {
            @Override
            public CalEvent apply(@Nullable final Object item) {
                if (item instanceof CalItem) {
                    return toCalEvent((CalItem) item);
                } else if (item instanceof Event) {
                    return toCalEvent((Event) item);
                } else {
                    throw new IllegalArgumentException("Unknown type [" + item + "].");
                }
            }
        });
        return Lists.newArrayList(events); // copy as recommended in javadoc.
    }

    private CalEvent toCalEvent(final Event event) {
        CalEvent calEvent = new CalEvent();
        calEvent.id = Event.class.getSimpleName() + "|" + event.getId();
        calEvent.entityId = event.getId();
        calEvent.clazzName = Event.class.getName();
        calEvent.title = event.getTeam().getName() + ": " + messageSource.getMessage(event.getEventType().getResourceKey());
        calEvent.start = event.getDateTime().toDate();
        calEvent.end = event.getDateTime().plusHours(1).toDate();
        calEvent.allDay = false;
        calEvent.className = "cal-type-event";
        // calEvent.url = linkProvider.deepLinkEvent(event.getId());
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

    @Override
    public void delete(final Long id) {
        CalItem entity = loadById(id);
        // no security assertions yet.
        calItemRepo.delete(entity);
    }
}
