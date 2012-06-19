package de.flower.rmt.model.db.entity;

import de.flower.common.model.db.entity.AbstractBaseEntity;
import de.flower.rmt.model.db.type.SplitDateTime;
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
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @NotNull
    @Column
    @org.hibernate.annotations.Type(type = "de.flower.rmt.model.db.type.PersistentSplitDateTime")
    @Index(name = "ix_startDate")
    private SplitDateTime startDate = new SplitDateTime();

    @NotNull
    @Column
    @org.hibernate.annotations.Type(type = "de.flower.rmt.model.db.type.PersistentSplitDateTime")
    @Index(name = "ix_endDate")
    private SplitDateTime endDate = new SplitDateTime();

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

    public CalItem() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public SplitDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(final SplitDateTime startDate) {
        this.startDate = startDate;
    }

    public void setStartDate(final DateTime dateTime) {
        this.startDate = new SplitDateTime(dateTime);
    }

    public SplitDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(final SplitDateTime endDate) {
        this.endDate = endDate;
    }

    public void setEndDate(final DateTime dateTime) {
        this.endDate = new SplitDateTime(dateTime);
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

    @Override
    public String toString() {
        return "CalItem{" +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", allDay=" + allDay +
                ", type=" + type +
                ", summary='" + summary + '\'' +
                "} " + super.toString();
    }
}
