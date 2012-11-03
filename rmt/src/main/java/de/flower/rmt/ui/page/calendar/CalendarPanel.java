package de.flower.rmt.ui.page.calendar;

import com.google.common.collect.ImmutableMap;
import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.rmt.model.db.entity.CalItem;
import de.flower.rmt.model.db.entity.CalItem_;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.dto.CalItemDto;
import de.flower.rmt.security.ISecurityService;
import de.flower.rmt.service.ICalendarManager;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.service.type.CalendarFilter;
import de.flower.rmt.ui.app.IPageResolver;
import de.flower.rmt.ui.app.RMTApplication;
import de.flower.rmt.ui.markup.html.calendar.CalEvent;
import de.flower.rmt.ui.markup.html.calendar.FullCalendarPanel;
import de.flower.rmt.ui.panel.RMTBasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;

/**
 * @author flowerrrr
 */
public abstract class CalendarPanel extends RMTBasePanel<List<CalendarFilter>> {

    @SpringBean
    private ICalendarManager calendarManager;

    @SpringBean
    private IEventManager eventManager;

    @SpringBean
    private ISecurityService securityService;

    @SpringBean
    private IPageResolver pageResolver;

    public CalendarPanel(String id, final IModel<List<CalendarFilter>> model) {
        super(id, model);

        add(new AjaxEventListener(CalItem.class));
        add(new AjaxEventListener(model));

        add(new FullCalendarPanel() {

            @Override
            protected void onEventClick(final AjaxRequestTarget target, final CalEvent calEvent) {
                if (CalItem.class.getName().equals(calEvent.clazzName)) {
                    CalItem calItem = calendarManager.loadById(calEvent.entityId, CalItem_.user);
                    boolean readonly = !(securityService.isCurrentUser(calItem.getUser()) || isManagerView());
                    if (readonly) {
                        CalendarPanel.this.onEventClick(target, calItem);
                    } else {
                        CalItemDto dto = CalItemDto.fromEntity(calItem);
                        CalendarPanel.this.onEventClick(target, dto, calItem.getUser());
                    }
                } else if (Event.class.getName().equals(calEvent.clazzName)) {
                    Event clubEvent = eventManager.loadById(calEvent.entityId);
                    CalendarPanel.this.onEventClick(target, clubEvent);
                } else if (calEvent.isNew()) {
                    CalItemDto dto = new CalItemDto();
                    // init time fields. when saved as allDay the time is adjusted to 0:00 - 23:59.
                    // helps displaying reasonable default values when displaying time fields the first time.
                    dto.setStartDateTime(new DateTime(calEvent.start).withTime(8, 0, 0, 0));
                    dto.setEndDateTime(new DateTime(calEvent.end).withTime(20, 0, 0, 0));
                    dto.setAllDay(calEvent.allDay);
                    dto.setAutoDecline(true);
                    CalendarPanel.this.onEventClick(target, dto, securityService.getUser());
                } else {
                    throw new IllegalArgumentException(calEvent.toString());
                }
            }

            @Override
            public List<CalEvent> loadCalEvents(final DateTime start, final DateTime end) {
                List<CalEvent> calEvents = calendarManager.findAllByCalendarAndRange(model.getObject(), start, end);
                return calEvents;
            }

            @Override
            protected Map<Integer, String> getErrorRedirectMap() {
                return ImmutableMap.of(RMTApplication.PAGE_EXPIRED_STATUS_CODE, this.urlFor(pageResolver.getPageExpiredPage(), null).toString());
            }
        });
    }

    protected abstract void onEventClick(AjaxRequestTarget target, CalItemDto calItemDto, User user);

    protected abstract void onEventClick(AjaxRequestTarget target, CalItem calItem);

    protected abstract void onEventClick(AjaxRequestTarget target, Event event);
}
