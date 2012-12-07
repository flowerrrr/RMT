package de.flower.rmt.model.db.type.activity;

import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.type.EventType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author flowerrrr
 */
public abstract class AbstractEventMessage implements Serializable {

    /**
     * Must have a serialVersionUID as instances are stored in database.
     */
    private static final long serialVersionUID = 1L;

    @NotNull
    private Long eventId;

    @NotNull
    private EventType eventType;

    @NotNull
    private Date eventDate;

    public AbstractEventMessage(final Event event) {
        setEventId(event.getId());
        setEventType(event.getEventType());
        setEventDate(event.getDateTimeAsDate());
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(final Long eventId) {
        this.eventId = eventId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(final EventType eventType) {
        this.eventType = eventType;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(final Date eventDate) {
        this.eventDate = eventDate;
    }

    @Override
    public String toString() {
        return "AbstractEventMessage{" +
                "eventId=" + eventId +
                ", eventType=" + eventType +
                ", eventDate=" + eventDate +
                '}';
    }
}
