package de.flower.rmt.ui.page.calendar.player;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.calendar.CalEvent;
import de.flower.common.ui.calendar.FullCalendarPanel;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.CalItem;
import de.flower.rmt.model.db.type.CalendarType;
import de.flower.rmt.model.dto.CalItemDto;
import de.flower.rmt.service.ICalendarManager;
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

    public CalendarPanel(final IModel<List<CalendarType>> model) {
        super(model);

        add(new AjaxEventListener(CalItem.class));
        add(new AjaxEventListener(model));

        add(new FullCalendarPanel() {

            @Override
            protected void onEdit(final AjaxRequestTarget target, final CalEvent calEvent) {
                CalItemDto dto;
                if (calEvent.isNew()) {
                    dto = new CalItemDto();
                    dto.setStartDateTime(new DateTime(calEvent.start));
                    dto.setEndDateTime(new DateTime(calEvent.end));
                    dto.setAllDay(calEvent.allDay);
                    dto.setAutoDecline(true);
                } else {
                    dto = CalItemDto.fromEntity(calendarManager.loadById(calEvent.entityId));
                }
                CalendarPanel.this.onEdit(target, dto);
            }

            @Override
            public List<CalEvent> loadCalEvents(final DateTime start, final DateTime end) {
                List<CalEvent> calEvents = calendarManager.findAllByCalendarAndRange(model.getObject(), start, end);
                return calEvents;
            }
        });
    }

    protected abstract void onEdit(AjaxRequestTarget target, CalItemDto calItemDto);
}
