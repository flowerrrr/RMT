package de.flower.rmt.model.db.entity;

import com.mysema.query.annotations.QueryInit;
import de.flower.common.model.db.entity.AbstractBaseEntity;
import org.hibernate.annotations.Index;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author flowerrrr
 */
@Entity
public class CalItem extends AbstractBaseEntity {

    public enum Type {
        HOLIDAY,
        INJURY,
        OTHER;

        public static String getResourceKey(final Type object) {
            return "calitem.type." + object.name().toLowerCase();
        }
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @QueryInit("club") // required when using QCalItem.calItem.user.club.eq(getClub()) in a query.
    @Index(name = "ix_user")
    private User user;

    @NotNull
    @Column
    @org.hibernate.annotations.Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    @Index(name = "ix_startDate")
    private DateTime startDateTime;

    @NotNull
    @Column
    @org.hibernate.annotations.Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    @Index(name = "ix_endDate")
    private DateTime endDateTime;

    @Column
    private boolean allDay;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Index(name = "ix_type")
    private Type type;

    /**
     * Can be edited by user when type==OTHER
     */
    @Column
    private String summary;

    @Column
    boolean autoDecline;

    public CalItem() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public DateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(final DateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public DateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(final DateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(final boolean allDay) {
        this.allDay = allDay;
    }

    public Type getType() {
        return type;
    }

    public void setType(final Type type) {
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

    @Override
    public String toString() {
        return "CalItem{" +
                "startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", allDay=" + allDay +
                ", type=" + type +
                ", summary='" + summary + '\'' +
                "} " + super.toString();
    }

    /**
     * @return true if start and end date are on same day.
     */
    public boolean isSingleDay() {
        return startDateTime.toLocalDate().equals(endDateTime.toLocalDate());
    }



}
