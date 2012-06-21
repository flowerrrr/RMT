package de.flower.rmt.ui.page.calendar.player;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.calendar.CalEvent;
import de.flower.common.ui.calendar.FullCalendarPanel;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.CalItem;
import de.flower.rmt.model.db.entity.CalItem_;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.type.CalendarType;
import de.flower.rmt.model.dto.CalItemDto;
import de.flower.rmt.service.ICalendarManager;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.service.security.ISecurityService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.DateTime;

import java.util.List;

/**
 * @author flowerrrr
 */
public abstract class CalendarPanel extends BasePanel {

    @SpringBean
    private ICalendarManager calendarManager;

    @SpringBean
    private IEventManager eventManager;

    @SpringBean
    private ISecurityService securityService;

    public CalendarPanel(String id, final IModel<List<CalendarType>> model) {
        super(id, model);

        add(new AjaxEventListener(CalItem.class));
        add(new AjaxEventListener(model));

        add(new FullCalendarPanel() {

            @Override
            protected void onEventClick(final AjaxRequestTarget target, final CalEvent calEvent) {
                if (CalItem.class.getName().equals(calEvent.clazzName)) {
                    CalItem calItem = calendarManager.loadById(calEvent.entityId, CalItem_.user);
                    boolean readonly = (securityService.isCurrentUser(calItem.getUser())) ? false : true;
                    if (readonly) {
                        CalendarPanel.this.onEventClick(target, calItem);
                    } else {
                        CalItemDto dto = CalItemDto.fromEntity(calItem);
                        CalendarPanel.this.onEventClick(target, dto);
                    }
                } else if (Event.class.getName().equals(calEvent.clazzName)) {
                    Event clubEvent = eventManager.loadById(calEvent.entityId);
                    CalendarPanel.this.onEventClick(target, clubEvent);
                } else if (calEvent.isNew()) {
                    CalItemDto dto = new CalItemDto();
                    dto.setStartDateTime(new DateTime(calEvent.start));
                    dto.setEndDateTime(new DateTime(calEvent.end));
                    dto.setAllDay(calEvent.allDay);
                    dto.setAutoDecline(true);
                    CalendarPanel.this.onEventClick(target, dto);
                } else {
                    throw new IllegalArgumentException(calEvent.toString());
                }
            }

            @Override
            public List<CalEvent> loadCalEvents(final DateTime start, final DateTime end) {
                List<CalEvent> calEvents = calendarManager.findAllByCalendarAndRange(model.getObject(), start, end);
                return calEvents;
            }
        });
    }

    protected abstract void onEventClick(AjaxRequestTarget target, CalItemDto calItemDto);

    protected abstract void onEventClick(AjaxRequestTarget target, CalItem calItem);

    protected abstract void onEventClick(AjaxRequestTarget target, Event event);
}
