package de.flower.rmt.model.dto;

import de.flower.common.model.db.entity.IEntity;
import de.flower.rmt.model.db.entity.CalItem;
import org.hibernate.validator.constraints.ScriptAssert;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author flowerrrr
 */
@ScriptAssert(script = "_this.isStartBeforeEnd()",
        message = "{validation.calitem.endbeforestart}", lang = "javascript"
)
public class CalItemDto implements IEntity, Serializable {

    private Long id;

    private Date startDate;

    private LocalTime startTime;

    private Date endDate;

    private LocalTime endTime;

    private boolean allDay;

    @NotNull(message = "{validation.calitem.type.notnull}")
    private CalItem.Type type;

    private String summary;

    private boolean autoDecline;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public boolean isNew() {
        return id == null;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(final LocalTime startTime) {
        this.startTime = startTime;
    }

    public DateTime getStartDateTime() {
        return new DateTime(startDate).withFields(startTime);
    }

    public void setStartDateTime(DateTime dateTime) {
        this.startDate = dateTime.toLocalDate().toDate();
        this.startTime = dateTime.toLocalTime();
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(final Date endDate) {
        this.endDate = endDate;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public DateTime getEndDateTime() {
         return new DateTime(endDate).withFields(endTime);
     }

     public void setEndDateTime(DateTime dateTime) {
        this.endDate = dateTime.toLocalDate().toDate();
        this.endTime = dateTime.toLocalTime();
    }

    public void setEndTime(final LocalTime endTime) {
        this.endTime = endTime;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(final boolean allDay) {
        this.allDay = allDay;
    }

    public CalItem.Type getType() {
        return type;
    }

    public void setType(final CalItem.Type type) {
        this.type = type;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(final String summary) {
        this.summary = summary;
    }

    public boolean isAutoDecline() {
        return autoDecline;
    }

    public void setAutoDecline(final boolean autoDecline) {
        this.autoDecline = autoDecline;
    }

    public boolean isStartBeforeEnd() {
        if (isAllDay()) {
            return startDate.getTime() <= endDate.getTime(); // start == end is ok
        } else {
            DateTime s = getStartDateTime();
            DateTime e = getEndDateTime();
            return !s.isAfter(e);
        }
    }

    public void copyTo(final CalItem entity) {
        entity.setStartDateTime(getStartDateTime());
        entity.setEndDateTime(getEndDateTime());
        entity.setAllDay(allDay);
        entity.setType(type);
        entity.setSummary(summary);
        entity.setAutoDecline(autoDecline);
    }

    public static CalItemDto fromEntity(final CalItem entity) {
        CalItemDto dto = new CalItemDto();
        dto.copyFrom(entity);
        return dto;
    }

    private void copyFrom(final CalItem entity) {
        id = entity.getId();
        setStartDateTime(entity.getStartDateTime());
        setEndDateTime(entity.getEndDateTime());
        allDay = entity.isAllDay();
        type = entity.getType();
        summary = entity.getSummary();
        autoDecline = entity.isAutoDecline();
    }
}
